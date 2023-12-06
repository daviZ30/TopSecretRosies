package com.moronlu18.invoice.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.moronlu18.InvoiceDavid.entity.InvoiceStatus
import com.moronlu18.invoice.Repository.ProviderInvoice
import com.moronlu18.invoice.adapter.AdaptadorArticulos
import com.moronlu18.invoice.entity.Factura
import com.moronlu18.invoiceFragment.R
import com.moronlu18.invoiceFragment.databinding.FilaArticulosBinding
import com.moronlu18.invoiceFragment.databinding.FragmentInvoiceDetailsBinding
import java.time.ZoneId


class InvoiceDetailsFragment : Fragment() {
    val facturas = ProviderInvoice.datasetFactura

    lateinit var factura: Factura;
    private var _binding: FragmentInvoiceDetailsBinding? = null
    private var _bindingAr: FilaArticulosBinding? = null

    private val binding
        get() = _binding!!
    private val bindingAr
        get() = _bindingAr!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInvoiceDetailsBinding.inflate(inflater, container, false)
        _bindingAr = FilaArticulosBinding.inflate(inflater,container,false)
        binding.rvInvoiceDetails.layoutManager = LinearLayoutManager(context)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.setFragmentResultListener("key",this, FragmentResultListener { requestKey, result ->
            var pos: Int = result.getInt("pos")
            factura = facturas[pos]
            var precios = factura.Articulos.map { it.rate }
            binding.rvInvoiceDetails.adapter = AdaptadorArticulos(factura.Articulos){
                Toast.makeText(requireContext(),"No puedes borrar un Articulo en la pestaña de detalles",Toast.LENGTH_LONG).show()
            }
            bindingAr.imgEliminarArticulo.visibility = View.GONE
            binding.txtInvoiceDetailsNombre.text = factura.Cliente.nombre
            binding.txtInvoiceDetailsEmail.text = factura.Cliente.email.value
            binding.txtInvoiceDetailsTelefono.text = factura.Cliente.telefono.toString()
            //val zoneId = ZoneId.systemDefault()
            val posEmi = factura.FeEmision.toString().indexOf('T')
            val posVen = factura.FeVencimiento.toString().indexOf('T')
            binding.txtInvoiceDetailsFechaEmision.text = factura.FeEmision.toString().substring(0,posEmi)
            binding.txtInvoiceDetailsFechaVencimiento.text = factura.FeVencimiento.toString().substring(0,posVen)
            var SubTotal = precios.reduce { acc, ar -> acc + ar}
            binding.txtInvoiceDetailsSubtotal.text =  "${SubTotal.toString()} €"
            binding.txtInvoiceDetailsImpuestos.text = "21 %"
            binding.txtInvoiceDetailsTotal.text =  String.format("%.2f €",SubTotal + (SubTotal * 0.21))
            ComRadio(factura.Estado)
            binding.rbInvoiceDetailsPendiente.isEnabled = false
            binding.rbInvoiceDetailsPagada.isEnabled = false
            binding.rbInvoiceDetailsPagadaVencida.isEnabled = false


        })
    }
fun ComRadio(s: InvoiceStatus){
    when(s){
        InvoiceStatus.Overdue ->{
            binding.rbInvoiceDetailsPagadaVencida.isChecked = true
        }
        InvoiceStatus.Paid ->{
            binding.rbInvoiceDetailsPagada.isChecked = true
        }
        InvoiceStatus.Pending ->{
            binding.rbInvoiceDetailsPendiente.isChecked = true
        }

        else -> {}
    }
}
}