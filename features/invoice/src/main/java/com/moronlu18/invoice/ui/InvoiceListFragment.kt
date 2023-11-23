package com.moronlu18.invoice.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.moronlu18.Repository.ProviderInvoice
import com.moronlu18.entity.Articulo
import com.moronlu18.entity.Factura
import com.moronlu18.invoice.adapter.AdaptadorFacturas
import com.moronlu18.invoiceFragment.R
import com.moronlu18.invoiceFragment.databinding.FragmentInvoiceListBinding


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

        binding.rvInvoiceList.adapter = AdaptadorFacturas(facturas){
            var bundle = Bundle();
            bundle.putInt("pos",it)
            parentFragmentManager.setFragmentResult("key",bundle)
            findNavController().navigate(R.id.action_invoiceListFragment_to_invoiceDetailsFragment)
        }
        binding.rvInvoiceList.layoutManager = LinearLayoutManager(context)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabInvoice.setOnClickListener {
            //findNavController().navigate()
           findNavController().navigate(R.id.action_invoiceListFragment_to_invoiceCreationFragment)
        }

    }


}