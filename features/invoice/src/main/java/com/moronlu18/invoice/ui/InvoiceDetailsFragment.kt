package com.moronlu18.invoice.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.moronlu18.InvoiceDavid.entity.InvoiceStatus
import com.moronlu18.invoice.adapter.AdaptadorArticulos
import com.moronlu18.invoice.entity.Factura
import com.moronlu18.invoice.usecase.InvoiceDetailsViewModel
import com.moronlu18.invoiceFragment.databinding.FilaArticulosBinding
import com.moronlu18.invoiceFragment.databinding.FragmentInvoiceDetailsBinding


class InvoiceDetailsFragment : Fragment() {

    lateinit var factura: Factura;
    private var _binding: FragmentInvoiceDetailsBinding? = null
    private var _bindingAr: FilaArticulosBinding? = null

    private val binding
        get() = _binding!!
    private val bindingAr
        get() = _bindingAr!!

    private val viewModel: InvoiceDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInvoiceDetailsBinding.inflate(inflater, container, false)
        _bindingAr = FilaArticulosBinding.inflate(inflater, container, false)
        binding.rvInvoiceDetails.layoutManager = LinearLayoutManager(context)
        binding.viewmodel = this.viewModel

        binding.lifecycleOwner = this

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.setFragmentResultListener(
            "key",
            this,
            FragmentResultListener { requestKey, result ->
                var pos: Int = result.getInt("pos")
                factura = viewModel.facturas[pos]
                var precios = factura.Articulos.map { it.precio }
                binding.rvInvoiceDetails.adapter = AdaptadorArticulos(factura.Articulos, true) {
                    Toast.makeText(
                        requireContext(),
                        "No puedes borrar un Articulo en la pestaña de detalles",
                        Toast.LENGTH_LONG
                    ).show()
                }
                bindingAr.imgEliminarArticulo.visibility = View.GONE
                binding.txtInvoiceDetailsNombre.text = viewModel.GetCliente(factura.idCliente)?.nombre
                binding.txtInvoiceDetailsEmail.text = viewModel.GetCliente(factura.idCliente)?.email?.value
                binding.txtInvoiceDetailsTelefono.text = viewModel.GetCliente(factura.idCliente)?.telefono.toString()
                //val zoneId = ZoneId.systemDefault()
                val posEmi = factura.FeEmision.toString().indexOf('T')
                val posVen = factura.FeVencimiento.toString().indexOf('T')
                binding.txtInvoiceDetailsFechaEmision.text =
                    factura.FeEmision.toString().substring(0, posEmi)
                binding.txtInvoiceDetailsFechaVencimiento.text =
                    factura.FeVencimiento.toString().substring(0, posVen)
                var SubTotal = precios.reduce { acc, ar -> acc + ar }
                binding.txtInvoiceDetailsSubtotal.text = String.format("%.2f €", SubTotal)
                binding.txtInvoiceDetailsImpuestos.text = "21 %"
                binding.txtInvoiceDetailsTotal.text =
                    String.format("%.2f €", SubTotal + (SubTotal * 0.21))
                ComRadio(factura.Estado)
                binding.rbInvoiceDetailsPendiente.isEnabled = false
                binding.rbInvoiceDetailsPagada.isEnabled = false
                binding.rbInvoiceDetailsPagadaVencida.isEnabled = false


            })
    }

    fun ComRadio(s: InvoiceStatus) {
        when (s) {
            InvoiceStatus.Overdue -> {
                binding.rbInvoiceDetailsPagadaVencida.isChecked = true
            }

            InvoiceStatus.Paid -> {
                binding.rbInvoiceDetailsPagada.isChecked = true
            }

            InvoiceStatus.Pending -> {
                binding.rbInvoiceDetailsPendiente.isChecked = true
            }

            else -> {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}