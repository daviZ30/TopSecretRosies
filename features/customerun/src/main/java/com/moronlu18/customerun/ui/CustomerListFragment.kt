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
import com.moronlu18.InvoiceDavid.Repository.InvoiceRepository
import com.moronlu18.customer.repository.CustomerRepository
import com.moronlu18.customerun.R
import com.moronlu18.customerun.adapter.CustomerAdapter
import com.moronlu18.customerun.databinding.FragmentCustomerListBinding
import com.moronlu18.customerun.usecase.CustomerListViewModel
import com.moronlu18.invoice.MainActivity
import com.moronlu18.task.repository.TaskRepository


class CustomerListFragment : Fragment(), MenuProvider {
    private var _binding: FragmentCustomerListBinding? = null

    private val binding get() = _binding!!

    private val viewModel: CustomerListViewModel by viewModels()


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

    override fun onStart() {
        super.onStart()
        viewModel.validate()
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

        // Inflate the layout for this fragment
        val adapter = CustomerAdapter({
            println(it)
            var bundle = Bundle().apply {
                putSerializable("customer", it)
            }
            findNavController().navigate(R.id.action_customerListFragment_to_customerDetailFragment2,bundle)
        }, { i: Int ->
            showDeleteConfirmationDialog(i)
        })
        viewModel.allcustomers.observe(viewLifecycleOwner) {
            it.let { adapter.submitList(it) }
        }
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_customerListFragment_to_customerCreationFragment2)
        }
        binding.listcustomer.adapter = adapter
        binding.listcustomer.adapter?.notifyDataSetChanged()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menuitem, menu)
    }

    private fun showDeleteConfirmationDialog(posicion: Int) {

        val builder = AlertDialog.Builder(requireContext())
        var rtarea = false
        var rinvoice = false
        for (task in TaskRepository.selectAllTaskListRAW()) {
            if (task.customer.id == viewModel.allcustomers.value?.get(posicion)!!.id) {
                rtarea = true
                break
            }
        }
        for (invoice in InvoiceRepository.getInvoiceListRAW()) {
            if (invoice.idCliente == viewModel.allcustomers.value?.get(posicion)!!.id) {
                rinvoice = true
                break
            }
        }
        when {
            rtarea -> {
                builder.setTitle("Cliente referenciado en Task")
                builder.setNegativeButton("Ok", null)
            }

            rinvoice -> {
                builder.setTitle("Cliente referenciado en Invoice")
                builder.setNegativeButton("Ok", null)
            }

            else -> {
                builder.setTitle("¿Deseas eliminar este Cliente?")
                builder.setPositiveButton("Eliminar") { _, _ ->
                    //Implementar eliminar con base de datos
                    CustomerRepository.delete(viewModel.allcustomers.value!![posicion])
                    if (viewModel.allcustomers.value!!.isEmpty()) {
                        binding.listcustomer.visibility = View.GONE
                        binding.textView5.visibility = View.VISIBLE
                    } else {
                        binding.listcustomer.visibility = View.VISIBLE
                        binding.textView5.visibility = View.GONE
                    }
                    binding.listcustomer.adapter?.notifyDataSetChanged()
                }
                builder.setNegativeButton("Cancel", null)
            }
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
                viewModel.sortName()
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
