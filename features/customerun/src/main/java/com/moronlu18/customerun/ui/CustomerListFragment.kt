package com.moronlu18.customerun.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.moronlu18.customer.repository.ProviderCustomer
import com.moronlu18.customerun.R
import com.moronlu18.customerun.adapter.CustomerAdapter
import com.moronlu18.customerun.databinding.FragmentCustomerListBinding
import com.moronlu18.customerun.usecase.CustomerListViewModel
import com.moronlu18.invoice.MainActivity
import com.moronlu18.task.repository.ProviderTask


class CustomerListFragment : Fragment(), MenuProvider {
    private var _binding: FragmentCustomerListBinding? = null

    private val binding get() = _binding!!

    private val viewModel: CustomerListViewModel by viewModels()

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
        binding.viewmodel = this.viewModel
        binding.lifecycleOwner = this
        setUpToolbar()
        binding.listcustomer.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (clientes.size < 1) {
            binding.listcustomer.visibility = View.GONE
            binding.textView5.visibility = View.VISIBLE
        } else {
            binding.listcustomer.visibility = View.VISIBLE
            binding.textView5.visibility = View.GONE
        }
        // Inflate the layout for this fragment
        val adapter = CustomerAdapter(viewModel.clientes, { i: Int, n: Int ->
            var bundle = Bundle();
            bundle.putInt("pos", i)
            parentFragmentManager.setFragmentResult("key", bundle)
            if (n == 0) {
                findNavController().navigate(R.id.action_customerListFragment_to_customerDetailFragment2)
            } else if (n == 1) {
                findNavController().navigate(R.id.action_customerListFragment_to_customerCreationFragment2)
            }

        }, { i: Int ->
            showDeleteConfirmationDialog(i)
        })
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_customerListFragment_to_customerCreationFragment2)
        }
        binding.listcustomer.adapter = adapter
        viewModel.validate()
        binding.listcustomer.adapter?.notifyDataSetChanged()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menuitem, menu)
    }

    private fun showDeleteConfirmationDialog(posicion: Int) {

        val builder = AlertDialog.Builder(requireContext())
        var eliminado = true
        for (task in ProviderTask.taskExample) {
            if (task.customer.id == clientes[posicion].id) {
                eliminado = false
                break
            }
        }
        if (eliminado) {
            builder.setTitle("¿Deseas eliminar esta factura?")
            builder.setPositiveButton("Eliminar") { _, _ ->
                viewModel.clientes.removeAt(posicion)
                if (viewModel.clientes.size < 1) {
                    binding.listcustomer.visibility = View.GONE
                    binding.textView5.visibility = View.VISIBLE
                } else {
                    binding.listcustomer.visibility = View.VISIBLE
                    binding.textView5.visibility = View.GONE
                }
                binding.listcustomer.adapter?.notifyDataSetChanged()
            }
            builder.setNegativeButton("Cancel", null)
        } else {
            builder.setTitle("Cliente referenciado en Task")
            builder.setNegativeButton("Ok", null)
        }


        builder.show()
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_refresh -> {
                viewModel.sortId()
                binding.listcustomer.adapter?.notifyDataSetChanged()
                return true
            }

            R.id.action_sort -> {
                viewModel.sortNombre()
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
