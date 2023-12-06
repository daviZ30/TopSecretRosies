package com.moronlu18.customerun.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.moronlu18.customer.repository.ProviderCustomer
import com.moronlu18.customerun.R
import com.moronlu18.customerun.adapter.CustomerAdapter
import com.moronlu18.customerun.databinding.FragmentCustomerListBinding


class CustomerListFragment : Fragment() {
    private var _binding: FragmentCustomerListBinding? = null

    private  val binding get()=_binding!!

    val clientes = ProviderCustomer.datasetCustomer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerListBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        binding.listcustomer.adapter = CustomerAdapter(clientes){i:Int,n:Int->
            var bundle = Bundle();
            bundle.putInt("pos",i)
            parentFragmentManager.setFragmentResult("key",bundle)
            if (n==0){
                findNavController().navigate(R.id.action_customerListFragment_to_customerDetailFragment2)
            }else if (n==1){
                findNavController().navigate(R.id.action_customerListFragment_to_customerCreationFragment2)
            }

        }
        binding.listcustomer.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_customerListFragment_to_customerCreationFragment2)
        }
    }
}
