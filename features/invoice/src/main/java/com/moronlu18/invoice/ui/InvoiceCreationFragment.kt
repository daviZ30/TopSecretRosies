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
import com.moronlu18.invoice.Repository.ProviderInvoice
import com.moronlu18.invoice.adapter.AdaptadorArticulos
import com.moronlu18.invoice.entity.Articulo
import com.moronlu18.invoice.entity.Factura
import com.moronlu18.invoiceFragment.databinding.FragmentInvoiceCreationBinding


//data class Articulo(val nombre:String,val precio:Double)
class InvoiceCreationFragment : Fragment() {
    val facturas = ProviderInvoice.datasetFactura
    lateinit var factura: Factura;

    private var _binding: FragmentInvoiceCreationBinding? = null
    private val binding
        get() = _binding!!

    val articulos = listOf<Articulo>(
        Articulo("Mesa",222.2),
        Articulo("Portaminas",1.0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragmentManager.setFragmentResultListener("key",this, FragmentResultListener { requestKey, result ->
            var pos: Int = result.getInt("pos")
            factura = facturas[pos]
            var precios = factura.Articulos.map { it.precio }
            binding.rvInvoiceArticulos.adapter = AdaptadorArticulos(factura.Articulos)
            binding.tieInvoiceCreationIdCliente.setText(factura.Cliente.id.toString())
            binding.tieInvoiceFeEmi.setText(factura.FeEmision)
            binding.tieInvoiceCreationFeVen.setText(factura.FeVencimiento)
            var SubTotal = precios.reduce { acc, ar -> acc + ar}
            binding.txtInvoiceCreationSubtotal.text =  "${SubTotal.toString()} €"
            binding.txtInvoiceCreationTotal.text =  String.format("%.2f €",SubTotal + (SubTotal * 0.21))
        })
    }
    inner class textWatcher(var t: Editable?) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable) {

            //binding.Email.requestFocus()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var precios = articulos.map { it.precio }
        var SubTotal = precios.reduce { acc, ar -> acc + ar}
        _binding = FragmentInvoiceCreationBinding.inflate(inflater, container, false)
        binding.rvInvoiceArticulos.adapter = AdaptadorArticulos(articulos)
        binding.rvInvoiceArticulos.layoutManager = LinearLayoutManager(context)
        binding.txtInvoiceCreationSubtotal.text =  "${SubTotal.toString()} €"
        binding.txtInvoiceCreationTotal.text =  String.format("%.2f €",SubTotal + (SubTotal * 0.21))
        binding.btnCrear.setOnClickListener{
            findNavController().popBackStack()
        }

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemListString = arrayListOf("Private", "Public", "Empty")
        val adapter =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_item, itemListString)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


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