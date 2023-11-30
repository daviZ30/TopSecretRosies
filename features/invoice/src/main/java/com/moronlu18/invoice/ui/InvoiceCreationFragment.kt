package com.moronlu18.invoice.ui

import android.R
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.FragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import com.moronlu18.customer.entity.Cliente
import com.moronlu18.customer.repository.ProviderCustomer
import com.moronlu18.invoice.Repository.ProviderInvoice
import com.moronlu18.invoice.adapter.AdaptadorArticulos
import com.moronlu18.invoice.entity.Articulo
import com.moronlu18.invoice.entity.Factura
import com.moronlu18.invoiceFragment.databinding.FragmentInvoiceCreationBinding


//data class Articulo(val nombre:String,val precio:Double)
class InvoiceCreationFragment : Fragment() {
    val facturas = ProviderInvoice.datasetFactura
    val clientes = ProviderCustomer.datasetCustomer
    lateinit var factura: Factura;
    lateinit var clien: Cliente;
    private var editar = false;

    private var _binding: FragmentInvoiceCreationBinding? = null
    private val binding
        get() = _binding!!

    val articulos = ProviderInvoice.datasetArticulo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    inner class textWatcher(var t: Editable?) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable) {
            println("illoooooo ${t.toString()}")

            try {
                var i: Int = t.toString().toInt()
                if (i != null) {
                    clientes.forEach {
                        if (it.id == i) {
                            clien = it
                            //println(clien)
                            binding.tvInvoiceCreationNombre.text = it.nombre
                            binding.tvInvoiceCreationEmail.text = it.email
                            binding.tvInvoiceCreationTelefono.text = it.telefono.toString()

                        }
                    }
                }
            } catch (e: Exception) {
                binding.tvInvoiceCreationNombre.text = ""
                binding.tvInvoiceCreationEmail.text = ""
                binding.tvInvoiceCreationTelefono.text = ""
            }

            //binding.Email.requestFocus()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentInvoiceCreationBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // val itemListString = articulos.map { articulo -> articulo.nombre }
        val adaptersp =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_item, articulos)

        adaptersp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val CreArticulos: MutableList<Articulo> = ArrayList<Articulo>()

        var rvadapter = AdaptadorArticulos(CreArticulos)
        binding.rvInvoiceArticulos.adapter = rvadapter
        binding.rvInvoiceArticulos.layoutManager = LinearLayoutManager(context)
        binding.btnCrear.setOnClickListener {
            //var a = Factura("");
            //factura.Articulos.add(a)
            //rvadapter.notifyDataSetChanged()
            //binding.rvInvoiceArticulos.scrollToPosition(factura.Articulos.size - 1)
            findNavController().popBackStack()
        }

        binding.spArticulo.adapter = adaptersp

        parentFragmentManager.setFragmentResultListener(
            "key",
            this,
            FragmentResultListener { requestKey, result ->
                editar = true
                var pos: Int = result.getInt("pos")
                factura = facturas[pos]
                var precios = factura.Articulos.map { it.precio }
                binding.rvInvoiceArticulos.adapter = AdaptadorArticulos(factura.Articulos)
                binding.tieInvoiceCreationIdCliente.setText(factura.Cliente.id.toString())
                binding.tieInvoiceFeEmi.setText(factura.FeEmision)
                binding.tieInvoiceCreationFeVen.setText(factura.FeVencimiento)
                var SubTotal = precios.reduce { acc, ar -> acc + ar }
                binding.txtInvoiceCreationSubtotal.text = "${SubTotal.toString()} €"
                binding.txtInvoiceCreationTotal.text =
                    String.format("%.2f €", SubTotal + (SubTotal * 0.21))


            })



        binding.btnArticulos.setOnClickListener {
            if (editar) {
                var b: String = binding.spArticulo.selectedItem.toString()
                var datos = b.split('-')
                var a = Articulo(datos[0], datos[1].substring(0, datos[1].length - 1).toDouble());
                factura.Articulos.add(a)
                rvadapter.notifyDataSetChanged()
                binding.rvInvoiceArticulos.scrollToPosition(factura.Articulos.size - 1)
            } else {
                var b: String = binding.spArticulo.selectedItem.toString()
                var datos = b.split('-')
                var a = Articulo(datos[0], datos[1].substring(0, datos[1].length - 1).toDouble());
                CreArticulos.add(a)
                rvadapter.notifyDataSetChanged()
                binding.rvInvoiceArticulos.scrollToPosition(CreArticulos.size - 1)
            }

        }


        binding.tieInvoiceCreationIdCliente.addTextChangedListener(textWatcher(binding.tieInvoiceCreationIdCliente.text))
        //Inicializar el listener que se lanza cuando el usuario modifica el valor

        /*
          viewModel.validate()

          viewModel.getState().observe(viewLifecycleOwner, Observer {
              when (it) {
                  SignUpState.EmailEmptyError -> setEmailEmptyError()
                  SignUpState.PasswordEmptyError -> setPasswordEmptyError()
                  //Se pode is cuando es un dataclass
                  is SignUpState.AuthencationError -> showMessage(it.message)
                  is SignUpState.Loading-> showProgessbar(it.value)
                  else -> onSuccess()
              }
          })

          //Se usa el modismo with que dado un objeto se puede modificar propiedades dentro dl bloque
          with(binding.spProfile) {
              this.adapter = adapter
              setSelection(2)
              onItemSelectedListener = listener
              onItemSelectedListener = null
          }*/
    }


}