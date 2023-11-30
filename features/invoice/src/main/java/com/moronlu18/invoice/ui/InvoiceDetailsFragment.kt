package com.moronlu18.invoice.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.moronlu18.invoice.Repository.ProviderInvoice
import com.moronlu18.invoice.adapter.AdaptadorArticulos
import com.moronlu18.invoice.entity.Factura
import com.moronlu18.invoiceFragment.databinding.FragmentInvoiceDetailsBinding


class InvoiceDetailsFragment : Fragment() {
    val facturas = ProviderInvoice.datasetFactura
    lateinit var factura: Factura;

    private var _binding: FragmentInvoiceDetailsBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragmentManager.setFragmentResultListener("key",this, FragmentResultListener { requestKey, result ->
            var pos: Int = result.getInt("pos")
            factura = facturas[pos]
            var precios = factura.Articulos.map { it.rate }
            binding.rvInvoiceDetails.adapter = AdaptadorArticulos(factura.Articulos)
            binding.txtInvoiceDetailsNombre.text = factura.Cliente.nombre
            binding.txtInvoiceDetailsEmail.text = factura.Cliente.email
            binding.txtInvoiceDetailsTelefono.text = factura.Cliente.telefono.toString()
            binding.txtInvoiceDetailsFechaEmision.text = factura.FeEmision.toString()
            binding.txtInvoiceDetailsFechaVencimiento.text = factura.FeVencimiento.toString()
            var SubTotal = precios.reduce { acc, ar -> acc + ar}
            binding.txtInvoiceDetailsSubtotal.text =  "${SubTotal.toString()} €"
            binding.txtInvoiceDetailsImpuestos.text = "21 %"
            binding.txtInvoiceDetailsTotal.text =  String.format("%.2f €",SubTotal + (SubTotal * 0.21))


        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInvoiceDetailsBinding.inflate(inflater, container, false)

        binding.rvInvoiceDetails.layoutManager = LinearLayoutManager(context)
        // Inflate the layout for this fragment
        return binding.root
    }

}