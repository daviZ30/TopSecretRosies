package com.moronlu18.invoice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.navigation.fragment.findNavController
import com.moronlu18.invoice.databinding.FragmentMainBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.btCustomerCreation.setOnClickListener {
            //findNavController().navigate()
           findNavController().navigate(R.id.action_mainFragment_to_customerCreationFragment)
        }*/
        /*binding.btCustomerDetails.setOnClickListener {
            //findNavController().navigate()
            findNavController().navigate(R.id.action_mainFragment_to_customerDetailFragment)
        }*/
        binding.btCustomerList.setOnClickListener {
            //findNavController().navigate()
            findNavController().navigate(R.id.action_mainFragment_to_customerListFragment)
        }
        /*binding.btInvoiceCreation.setOnClickListener {
            //findNavController().navigate()
            findNavController().navigate(R.id.action_mainFragment_to_invoiceCreationFragment)
        }*/
        /*binding.btInvoiceDetails.setOnClickListener {
            //findNavController().navigate()
            findNavController().navigate(R.id.action_mainFragment_to_invoicedetailFragment)
        }*/
        binding.btInvoiceList.setOnClickListener {
            //findNavController().navigate()
            findNavController().navigate(R.id.action_mainFragment_to_invoiceFragment)
        }
        /*binding.btItemCreation.setOnClickListener {
            //findNavController().navigate()
            findNavController().navigate(R.id.action_mainFragment_to_itemcreationFragment)
        }*/
       /* binding.btItemDetail.setOnClickListener {
            //findNavController().navigate()
           findNavController().navigate(R.id.action_mainFragment_to_itemdetailFragment)
        }*/
        binding.btItemList.setOnClickListener {
            //findNavController().navigate()
           findNavController().navigate(R.id.action_mainFragment_to_itemFragment)
        }
       /* binding.btTaskCreation.setOnClickListener {
            //findNavController().navigate()
            findNavController().navigate(R.id.action_mainFragment_to_taskcreationFragment)
        }*/
        /*binding.btTaskDetails.setOnClickListener {
            //findNavController().navigate()
            findNavController().navigate(R.id.action_mainFragment_to_taskdetailFragment)
        }*/
        binding.btTaskList.setOnClickListener {
            //findNavController().navigate()
            findNavController().navigate(R.id.action_mainFragment_to_tasklistFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}