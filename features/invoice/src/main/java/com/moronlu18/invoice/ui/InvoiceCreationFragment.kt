package com.moronlu18.invoice.ui

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import com.moronlu18.InvoiceDavid.entity.InvoiceStatus
import com.moronlu18.customer.entity.Cliente
import com.moronlu18.customer.repository.ProviderCustomer
import com.moronlu18.invoice.Repository.ProviderInvoice
import com.moronlu18.invoice.adapter.AdaptadorArticulos
import com.moronlu18.invoice.entity.Factura
import com.moronlu18.invoice.usecase.InvoiceViewModel
import com.moronlu18.invoiceFragment.databinding.FragmentInvoiceCreationBinding
import com.moronlu18.item.entity.item
import com.moronlu18.item.repository.ItemRepository


//data class Articulo(val nombre:String,val precio:Double)
class InvoiceCreationFragment : Fragment() {
    val facturas = ProviderInvoice.datasetFactura
    val clientes = ProviderCustomer.datasetCustomer
    lateinit var factura: Factura;
    lateinit var clien: Cliente;
    private var editar = false;
    var CreArticulos: MutableList<item> = ArrayList<item>()


    private var _binding: FragmentInvoiceCreationBinding? = null
    private val binding
        get() = _binding!!

    val articulos = ItemRepository().getItemList()

    private val viewModel: InvoiceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    inner class textWatcher(var e: Editable?, var t: TextInputLayout) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable) {
            // println("illoooooo ${t.toString()}")
            if (e != null) {
                rellenarCliente(e)
            }
            t.isErrorEnabled = false
            //binding.Email.requestFocus()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInvoiceCreationBinding.inflate(inflater, container, false)
        binding.viewmodel = this.viewModel

        binding.lifecycleOwner = this




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // val itemListString = articulos.map { articulo -> articulo.nombre }
        val adaptersp =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_item, articulos)

        adaptersp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        viewModel.getState().observe(viewLifecycleOwner) {
            when (it) {
                InvoiceState.idClienteEmtyError -> {
                    println("1111")
                    binding.tilInvoiceCreationIdCliente.error =
                        "Introduce un id de Cliente existente"
                    binding.tilInvoiceCreationIdCliente.requestFocus()
                }

                InvoiceState.idFacturaEmtyError -> {
                    println("2222")
                    // if(editar == false){
                    binding.tilInvoiceCreationIdFactura.error = "Introduce un id para la factura"
                    //}
                    binding.tilInvoiceCreationIdFactura.requestFocus()
                }

                else -> Validate()
            }


        }

        var rvadapter = AdaptadorArticulos(CreArticulos)
        binding.rvInvoiceArticulos.adapter = rvadapter
        binding.rvInvoiceArticulos.layoutManager = LinearLayoutManager(context)
        binding.spArticulo.adapter = adaptersp



        parentFragmentManager.setFragmentResultListener(
            "key",
            this,
            FragmentResultListener { requestKey, result ->
                editar = true
                var pos: Int = result.getInt("pos")
                factura = facturas[pos]
                var precios = factura.Articulos.map { it.rate }
                binding.rvInvoiceArticulos.adapter = AdaptadorArticulos(factura.Articulos)
                binding.tieInvoiceCreationIdCliente.setText(factura.Cliente.id.toString())
                viewModel.idFactura.value = factura.Cliente.id.toString()
                //viewModel.idCliente.value = factura.id.toString()
                rellenarCliente(binding.tieInvoiceCreationIdCliente.text)
                /*binding.tieInvoiceCreationIdCliente.addTextChangedListener(
                    textWatcherCliente(
                        binding.tieInvoiceCreationIdCliente.text
                    )
                )*/
                binding.tieInvoiceFeEmi.setText(factura.FeEmision.toString())
                binding.tieInvoiceCreationFeVen.setText(factura.FeVencimiento.toString())
                var SubTotal = precios.reduce { acc, ar -> acc + ar }
                binding.txtInvoiceCreationSubtotal.text = "${SubTotal.toString()} €"
                binding.txtInvoiceCreationTotal.text =
                    String.format("%.2f €", SubTotal + (SubTotal * 0.21))
                //binding.tilInvoiceCreationIdFactura.isErrorEnabled = false
                binding.tieInvoiceCreationIdCliente.addTextChangedListener(
                    textWatcher(
                        binding.tieInvoiceCreationIdCliente.text,
                        binding.tilInvoiceCreationIdCliente
                    )
                )
                binding.tieInvoiceCreationIdFactura.addTextChangedListener(
                    textWatcher(
                        null,
                        binding.tilInvoiceCreationIdFactura
                    )
                )

            })
        viewModel.validate()



        binding.btnArticulos.setOnClickListener {
            if (editar) {
                var b: String = binding.spArticulo.selectedItem.toString()
                var datos = b.split('-')

                var a = ObtenerItem(datos[0])
                factura.Articulos.add(a!!)
                rvadapter.notifyDataSetChanged()
                binding.rvInvoiceArticulos.scrollToPosition(factura.Articulos.size - 1)
            } else {
                var b: String = binding.spArticulo.selectedItem.toString()
                var datos = b.split('-')
                var a = ObtenerItem(datos[0])
                CreArticulos.add(a!!)
                rvadapter.notifyDataSetChanged()
                binding.rvInvoiceArticulos.scrollToPosition(CreArticulos.size - 1)
            }

        }

        binding.btnCrear.setOnClickListener {
            viewModel.validate()
        }

        binding.tieInvoiceCreationIdCliente.addTextChangedListener(
            textWatcher(
                binding.tieInvoiceCreationIdCliente.text,
                binding.tilInvoiceCreationIdCliente
            )
        )
        binding.tieInvoiceCreationIdFactura.addTextChangedListener(
            textWatcher(
                null,
                binding.tilInvoiceCreationIdFactura
            )
        )


    }

    fun Validate() {
        println("3333")
        if (editar) {
            when {
                factura.Articulos.size == 0 -> Toast.makeText(
                    requireContext(),
                    "Introduce algún artículo",
                    Toast.LENGTH_SHORT
                ).show()

                /*binding.tvInvoiceCreationNombre.text.isEmpty() -> Toast.makeText(
                    requireContext(),
                    "Introduce un cliente",
                    Toast.LENGTH_SHORT
                ).show()*/

                nuevoId(binding.tieInvoiceCreationIdFactura.text.toString()) -> {
                    binding.tilInvoiceCreationIdFactura.error =
                        "Id de la factura invalido, para editar el id debe existir"
                    binding.tilInvoiceCreationIdFactura.requestFocus()
                }

                else -> CrearFactura(editar)
            }
        } else {
            when {
                CreArticulos.size == 0 -> Toast.makeText(
                    requireContext(),
                    "Introduce algún artículo",
                    Toast.LENGTH_SHORT
                ).show()

                /*binding.tvInvoiceCreationNombre.text.isEmpty() -> Toast.makeText(
                    requireContext(),
                    "Introduce un cliente",
                    Toast.LENGTH_SHORT
                ).show()*/

                !validarIdFactura(binding.tieInvoiceCreationIdFactura.text.toString()) -> {
                    binding.tilInvoiceCreationIdFactura.error =
                        "Id de la factura invalido, debe ser un id no existente"
                    binding.tilInvoiceCreationIdFactura.requestFocus()
                }


                else -> CrearFactura(editar)
            }
        }
    }

    fun nuevoId(cadena: String): Boolean {
        try {
            var i = cadena.toInt()
            facturas.forEach {
                if (it.id == i) {
                    return false
                }
            }
        } catch (e: Exception) {
            return false
        }
        return true
    }

    fun validarIdFactura(cadena: String): Boolean {
        try {
            var i = cadena.toInt()
            facturas.forEach {
                if (it.id == i) {
                    return false
                }
            }
        } catch (e: Exception) {
            return false
        }
        return true
    }

    fun rellenarCliente(t: Editable?) {
        try {
            var i: Int = t.toString().toInt()
            if (i != null) {
                clientes.forEach {
                    if (it.id == i) {
                        clien = it
                        //println(clien)
                        binding.tvInvoiceCreationNombre.text = it.nombre
                        binding.tvInvoiceCreationEmail.text = it.email.value
                        binding.tvInvoiceCreationTelefono.text = it.telefono.toString()

                    }
                }
            }
        } catch (e: Exception) {
            binding.tvInvoiceCreationNombre.text = ""
            binding.tvInvoiceCreationEmail.text = ""
            binding.tvInvoiceCreationTelefono.text = ""
        }
    }

    fun CrearFactura(editar: Boolean) {
        if (editar) {
            facturas.remove(
                ObtenerFactura(
                    binding.tieInvoiceCreationIdFactura.text.toString().toInt()
                )
            )
            var f = Factura(
                binding.tieInvoiceCreationIdFactura.text.toString().toInt(),
                clien,
                binding.tieInvoiceFeEmi.text.toString(),
                binding.tieInvoiceCreationFeVen.text.toString(),
                factura.Articulos,
                InvoiceStatus.Pending
            )
            facturas.add(f)

        } else {
            var f = Factura(
                binding.tieInvoiceCreationIdFactura.text.toString().toInt(),
                clien,
                binding.tieInvoiceFeEmi.text.toString(),
                binding.tieInvoiceCreationFeVen.text.toString(),
                CreArticulos,
                InvoiceStatus.Pending
            )
            facturas.add(f)

        }
        /*var bundle = Bundle();
        bundle.putInt("pos",1)
        parentFragmentManager.setFragmentResult("key",bundle)*/
        findNavController().popBackStack()

    }

    fun ObtenerItem(nombre: String): item? {

        articulos.forEach {

            if (nombre.trim().equals(it.name)) {

                return it
            }
        }
        return null;
    }

    fun ObtenerFactura(id: Int): Factura? {
        facturas.forEach {
            if (id == it.id) {

                return it
            }
        }
        return null;
    }

}