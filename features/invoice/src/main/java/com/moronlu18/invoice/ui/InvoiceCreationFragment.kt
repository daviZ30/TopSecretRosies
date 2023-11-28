package com.moronlu18.invoice.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
            binding.tieInvoiceCreationNombre.setText(factura.Cliente)
            binding.tieInvoiceCreationEmail.setText(factura.Email)
            binding.tieInvoiceCreationTelefono.setText(factura.Telefono.toString())
            binding.tieInvoiceFeEmi.setText(factura.FeEmision)
            binding.tieInvoiceCreationFeVen.setText(factura.FeVencimiento)
            var SubTotal = precios.reduce { acc, ar -> acc + ar}
            binding.txtInvoiceCreationSubtotal.text =  "${SubTotal.toString()} €"
            binding.txtInvoiceCreationTotal.text =  String.format("%.2f €",SubTotal + (SubTotal * 0.21))


        })
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


}