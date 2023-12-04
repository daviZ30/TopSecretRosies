package com.moronlu18.invoice.ui

import android.R
import android.content.ClipData.Item
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import java.time.Instant


//data class Articulo(val nombre:String,val precio:Double)
class InvoiceCreationFragment : Fragment() {
    val facturas = ProviderInvoice.datasetFactura
    val clientes = ProviderCustomer.datasetCustomer
    lateinit var factura: Factura;
    var clien: Cliente? = null;
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
                    binding.tilInvoiceCreationIdCliente.error =
                        "Introduce un id de Cliente existente"
                    binding.tilInvoiceCreationIdCliente.requestFocus()
                }

                InvoiceState.idFacturaEmtyError -> {
                    binding.tilInvoiceCreationIdFactura.error = "Introduce un id para la factura"
                    binding.tilInvoiceCreationIdFactura.requestFocus()
                }

                InvoiceState.feVenEmtyError -> {
                    binding.tilInvoiceCreationFeVen.error =
                        "Introduce una fecha con formato YYYY/MM/DD"
                    binding.tilInvoiceCreationFeVen.requestFocus()
                }

                InvoiceState.feEmiEmtyError -> {
                    binding.tilInvoiceFeEmi.error =
                        "Introduce una fecha con formato YYYY/MM/DD"
                    binding.tilInvoiceFeEmi.requestFocus()
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
                val posEmi = factura.FeEmision.toString().indexOf('T')
                val posVen = factura.FeVencimiento.toString().indexOf('T')

                binding.tieInvoiceFeEmi.setText(factura.FeEmision.toString().substring(0, posEmi))
                binding.tieInvoiceCreationFeVen.setText(
                    factura.FeVencimiento.toString().substring(0, posVen)
                )
                var SubTotal = precios.reduce { acc, ar -> acc + ar }
                binding.txtInvoiceCreationSubtotal.text = String.format("%.2f €", SubTotal)
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
            updateTotal()

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
        binding.tieInvoiceFeEmi.addTextChangedListener(
            textWatcher(
                null,
                binding.tilInvoiceFeEmi
            )
        )
        binding.tieInvoiceCreationFeVen.addTextChangedListener(
            textWatcher(
                null,
                binding.tilInvoiceCreationFeVen
            )
        )


    }

    private fun updateTotal() {
        if (editar) {
            var precios = factura.Articulos.map { it.rate }
            var SubTotal = precios.reduce { acc, ar -> acc + ar }
            binding.txtInvoiceCreationSubtotal.text = "${SubTotal.toString()} €"
            binding.txtInvoiceCreationTotal.text =
                String.format("%.2f €", SubTotal + (SubTotal * 0.21))
        } else {
            var precios = CreArticulos.map { it.rate }
            var SubTotal = precios.reduce { acc, ar -> acc + ar }
            binding.txtInvoiceCreationSubtotal.text = String.format("%.2f €", SubTotal)
            binding.txtInvoiceCreationTotal.text =
                String.format("%.2f €", SubTotal + (SubTotal * 0.21))
        }

    }

    private fun ValidarFecha(s: String): Boolean {
        try {
            val dateString = s + "T00:00:00Z"
            Instant.parse(dateString)

        } catch (e: Exception) {
            return false
        }
        return true
    }

    private fun fechaMayor(t1: Instant, t2: Instant): Boolean {
        return t1.isBefore(t2)

    }

    fun Validate() {
        if (editar) {
            when {
                factura.Articulos.size == 0 -> Toast.makeText(
                    requireContext(),
                    "Introduce algún artículo",
                    Toast.LENGTH_SHORT
                ).show()

                !rellenarCliente(binding.tieInvoiceCreationIdCliente.text) -> {
                    binding.tilInvoiceCreationIdCliente.error =
                        "Introduce un id de cliente existente"
                    binding.tilInvoiceCreationIdCliente.requestFocus()
                }

                !ValidarFecha(binding.tieInvoiceFeEmi.text.toString()) -> {
                    binding.tilInvoiceFeEmi.error = "Formato de la fecha no válido, YYYY-MM-DD"
                    binding.tilInvoiceFeEmi.requestFocus()
                }

                !ValidarFecha(binding.tieInvoiceCreationFeVen.text.toString()) -> {
                    binding.tilInvoiceCreationFeVen.error =
                        "Formato de la fecha no válido, YYYY-MM-DD"
                    binding.tilInvoiceCreationFeVen.requestFocus()
                }


                nuevoId(binding.tieInvoiceCreationIdFactura.text.toString()) -> {
                    binding.tilInvoiceCreationIdFactura.error =
                        "Id de la factura invalido, para editar el id debe existir"
                    binding.tilInvoiceCreationIdFactura.requestFocus()
                }

                !fechaMayor(
                    SetFecha(binding.tieInvoiceFeEmi.text.toString()),
                    SetFecha(binding.tieInvoiceCreationFeVen.text.toString())
                ) -> {
                    Toast.makeText(
                        requireContext(),
                        "La fecha de vencimiento debe ser mayor que la fecha de Emisión",
                        Toast.LENGTH_LONG
                    ).show()
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

                !rellenarCliente(binding.tieInvoiceCreationIdCliente.text) -> {
                    binding.tilInvoiceCreationIdCliente.error =
                        "Introduce un id de cliente existente"
                    binding.tilInvoiceCreationIdCliente.requestFocus()
                }

                !ValidarFecha(binding.tieInvoiceFeEmi.text.toString()) -> {
                    binding.tilInvoiceFeEmi.error = "Formato de la fecha no válido, YYYY-MM-DD"
                    binding.tilInvoiceFeEmi.requestFocus()
                }

                !ValidarFecha(binding.tieInvoiceCreationFeVen.text.toString()) -> {
                    binding.tilInvoiceCreationFeVen.error =
                        "Formato de la fecha no válido, YYYY-MM-DD"
                    binding.tilInvoiceCreationFeVen.requestFocus()
                }

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
            return true
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

    fun rellenarCliente(t: Editable?): Boolean {
        try {
            var i: Int = t.toString().toInt()
            if (i != null) {
                clientes.forEach {
                    println("forEach")
                    if (it.id == i) {
                        println("dentro del if")
                        clien = it
                        //println(clien)
                        binding.tvInvoiceCreationNombre.text = it.nombre
                        binding.tvInvoiceCreationEmail.text = it.email.value
                        binding.tvInvoiceCreationTelefono.text = it.telefono.toString()
                        return true
                    }
                }
            }
        } catch (e: Exception) {
            binding.tvInvoiceCreationNombre.text = ""
            binding.tvInvoiceCreationEmail.text = ""
            binding.tvInvoiceCreationTelefono.text = ""
            return false
        }
        return false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun SetFecha(fecha: String): Instant {
        val dateString = fecha + "T00:00:00Z"
        //val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        //val localDateTime = LocalDateTime.parse(dateString, formatter)
        val instant = Instant.parse(dateString)
        //return localDateTime.toInstant(ZoneOffset.MAX)
        return instant


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun CrearFactura(editar: Boolean) {
        if (editar) {
            facturas.remove(
                ObtenerFactura(
                    binding.tieInvoiceCreationIdFactura.text.toString().toInt()
                )
            )
            var f = Factura(
                binding.tieInvoiceCreationIdFactura.text.toString().toInt(),
                clien!!,
                SetFecha(binding.tieInvoiceFeEmi.text.toString()),
                SetFecha(binding.tieInvoiceCreationFeVen.text.toString()),
                factura.Articulos,
                InvoiceStatus.Pending
            )
            facturas.add(f)

        } else {
            var f = Factura(
                binding.tieInvoiceCreationIdFactura.text.toString().toInt(),
                clien!!,
                SetFecha(binding.tieInvoiceFeEmi.text.toString()),
                SetFecha(binding.tieInvoiceCreationFeVen.text.toString()),
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