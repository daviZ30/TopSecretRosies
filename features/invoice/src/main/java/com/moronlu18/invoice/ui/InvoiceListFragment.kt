package com.moronlu18.invoice.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.moronlu18.invoice.Repository.ProviderInvoice
import com.moronlu18.invoice.adapter.AdaptadorFacturas
import com.moronlu18.invoiceFragment.R
import com.moronlu18.invoiceFragment.databinding.FragmentInvoiceListBinding


class InvoiceListFragment : Fragment() {
    private var _binding: FragmentInvoiceListBinding? = null
    private val binding
        get() = _binding!!

    val facturas = ProviderInvoice.datasetFactura
    /*fun ViewImage(){
        if (facturas.isEmpty()) {
            binding.rvInvoiceList.visibility = View.GONE
            binding.imgNada.visibility = View.VISIBLE
        } else {
            binding.rvInvoiceList.visibility = View.VISIBLE
            binding.imgNada.visibility = View.GONE
        }
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //ViewImage()
        _binding = FragmentInvoiceListBinding.inflate(inflater, container, false)

        var adapter = AdaptadorFacturas(facturas){ i:Int, n:Int ->
            var bundle = Bundle();
            bundle.putInt("pos",i)
            parentFragmentManager.setFragmentResult("key",bundle)
            if(n == 0){
                findNavController().navigate(R.id.action_invoiceListFragment_to_invoiceDetailsFragment)
            }else if( n == 1){
                findNavController().navigate(R.id.action_invoiceListFragment_to_invoiceCreationFragment)
            }

        }
        binding.rvInvoiceList.adapter = adapter
        adapter.notifyDataSetChanged()
        binding.rvInvoiceList.scrollToPosition(facturas.size - 1)
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