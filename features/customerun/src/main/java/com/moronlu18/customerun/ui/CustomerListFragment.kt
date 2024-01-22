package com.moronlu18.customerun.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.moronlu18.customer.repository.ProviderCustomer
import com.moronlu18.customerun.R
import com.moronlu18.customerun.adapter.CustomerAdapter
import com.moronlu18.customerun.databinding.FragmentCustomerListBinding
import com.moronlu18.invoice.MainActivity


class CustomerListFragment : Fragment(), MenuProvider {
    private var _binding: FragmentCustomerListBinding? = null

    private val binding get() = _binding!!

    val clientes = ProviderCustomer.datasetCustomer

    private fun setUpToolbar() {
        (requireActivity() as? MainActivity)?.toolbar?.apply {
            visibility = View.VISIBLE
        }
        val menuhost: MenuHost = requireActivity()
        menuhost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerListBinding.inflate(inflater, container, false)
        setUpToolbar()
        if (clientes.size < 1) {
            binding.textView5.visibility = View.VISIBLE
            binding.listcustomer.visibility = View.GONE
        } else {
            binding.textView5.visibility = View.GONE
            binding.listcustomer.visibility = View.VISIBLE
        }
        // Inflate the layout for this fragment
        binding.listcustomer.adapter = CustomerAdapter(clientes) { i: Int, n: Int ->
            var bundle = Bundle();
            bundle.putInt("pos", i)
            parentFragmentManager.setFragmentResult("key", bundle)
            if (n == 0) {
                findNavController().navigate(R.id.action_customerListFragment_to_customerDetailFragment2)
            } else if (n == 1) {
                findNavController().navigate(R.id.action_customerListFragment_to_customerCreationFragment2)
            }

        }
        binding.listcustomer.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_customerListFragment_to_customerCreationFragment2)
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menuitem, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_refresh -> {
                ProviderCustomer.datasetCustomer.sortBy { it.id }
                binding.listcustomer.adapter?.notifyDataSetChanged()
                return true
            }

            R.id.action_sort -> {
                ProviderCustomer.datasetCustomer.sortBy { it.nombre }
                binding.listcustomer.adapter?.notifyDataSetChanged()
                return true
            }

            else -> return false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
