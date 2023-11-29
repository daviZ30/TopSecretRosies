package com.moronlu18.customerun.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import com.moronlu18.customer.entity.Cliente
import com.moronlu18.customer.repository.ProviderCustomer
import com.moronlu18.customerun.databinding.FragmentCustomerDetailBinding

class CustomerDetailFragment : Fragment() {
    var clientes = ProviderCustomer.datasetCustomer
    lateinit var cliente: Cliente

    private var _binding: FragmentCustomerDetailBinding? = null

    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragmentManager.setFragmentResultListener(
            "key",
            this,
            FragmentResultListener { requestKey, result ->
                var pos: Int = result.getInt("pos")
                cliente = clientes[pos]
                binding.txvnombreCustomerDetail.text = cliente.nombre + " " + cliente.apellidos
                binding.txvidCustomerDetail.text = cliente.id.toString()
                binding.txvemailCustomerDetail.text = cliente.email
                binding.txvciudadCustomerDetail.text = cliente.city
                binding.txvdireccionCustomerDetail.text = cliente.direction
                binding.txvtelefonoCustomerDetail.text = cliente.telefono.toString()
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerDetailBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

}