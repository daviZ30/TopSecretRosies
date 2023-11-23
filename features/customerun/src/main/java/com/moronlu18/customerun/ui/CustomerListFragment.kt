package com.moronlu18.customerun.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransitionImpl
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moronlu18.customerun.R
import com.moronlu18.customerun.adapter.CustomerAdapter
import com.moronlu18.customerun.databinding.FragmentCustomerListBinding

data class Cliente(val nombre: String, val apellidos: String, val email: String)
class CustomerListFragment : Fragment() {
    private var _binding: FragmentCustomerListBinding? = null

    private  val binding get()=_binding!!

    val clientes = listOf<Cliente>(
        Cliente("Alex", "Carnero", "carnero@gmail.com"),
        Cliente("Alex", "Carnero", "carnero@gmail.com"),
        Cliente("Alex", "Carnero", "carnero@gmail.com")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerListBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        binding.listcustomer.adapter = CustomerAdapter(clientes){
            findNavController().navigate(R.id.action_customerListFragment_to_customerDetailFragment2)
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