package com.moronlu18.invoice.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.moronlu18.Repository.ProviderInvoice
import com.moronlu18.entity.Articulo
import com.moronlu18.entity.Factura
import com.moronlu18.invoice.adapter.AdaptadorFacturas
import com.moronlu18.invoicelist.databinding.FragmentInvoiceListBinding


class InvoiceListFragment : Fragment() {
    private var _binding: FragmentInvoiceListBinding? = null
    private val binding
        get() = _binding!!

    val facturas = ProviderInvoice.datasetFactura

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentInvoiceListBinding.inflate(inflater, container, false)

        binding.rvInvoiceList.adapter = AdaptadorFacturas(facturas)
        binding.rvInvoiceList.layoutManager = LinearLayoutManager(context)

        return binding.root
    }


}