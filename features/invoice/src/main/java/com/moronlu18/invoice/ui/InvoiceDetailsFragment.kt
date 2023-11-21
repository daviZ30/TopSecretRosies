package com.moronlu18.invoice.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moronlu18.Repository.ProviderInvoice
import com.moronlu18.entity.Articulo
import com.moronlu18.invoice.adapter.AdaptadorArticulos
import com.moronlu18.invoicelist.databinding.FragmentInvoiceDetailsBinding


class InvoiceDetailsFragment : Fragment() {

    private var _binding: FragmentInvoiceDetailsBinding? = null
    private val binding
        get() = _binding!!

    val articulos = ProviderInvoice.datasetArticulo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInvoiceDetailsBinding.inflate(inflater, container, false)

        binding.rvInvoiceDetails.adapter = AdaptadorArticulos(articulos)
        binding.rvInvoiceDetails.layoutManager = LinearLayoutManager(context)
        // Inflate the layout for this fragment
        return binding.root
    }

}