package com.moronlu18.invoice.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.moronlu18.invoice.MainActivity
import com.moronlu18.invoice.adapter.AdaptadorFacturas
import com.moronlu18.invoice.ui.utils.Utils
import com.moronlu18.invoice.usecase.InvoiceListViewModel
import com.moronlu18.invoiceFragment.R
import com.moronlu18.invoiceFragment.databinding.FragmentInvoiceListBinding


class InvoiceListFragment : Fragment(), MenuProvider {
    private var _binding: FragmentInvoiceListBinding? = null
    private val binding
        get() = _binding!!

    lateinit var adapterInvoice: AdaptadorFacturas

    /*fun ViewImage(){
      if (facturas.isEmpty()) {
          binding.rvInvoiceList.visibility = View.GONE
          binding.imgNada.visibility = View.VISIBLE
      } else {
          binding.rvInvoiceList.visibility = View.VISIBLE
          binding.imgNada.visibility = View.GONE
      }
  }*/
    private val viewModel: InvoiceListViewModel by viewModels()


    private fun setUpToolbar() {
        //Modismo aplly de kotlin
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
        //ViewImage()
        _binding = FragmentInvoiceListBinding.inflate(inflater, container, false)
        binding.viewmodel = this.viewModel
        binding.lifecycleOwner = this
        setUpToolbar()

        //adapter.notifyDataSetChanged()
        //binding.rvInvoiceList.scrollToPosition(facturas.size - 1)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUserRecycler()
        viewModel.getState().observe(viewLifecycleOwner) {
            when (it) {
                is InvoiceListState.noDataError -> {
                    binding.imgNada.visibility = View.VISIBLE
                    binding.rvInvoiceList.visibility = View.GONE
                    Utils.showSnackBar(binding.root,"Lista vacia")

                }
                is InvoiceListState.Success -> onSuccess()
            }
        }

        if (viewModel.facturas.size < 1) {
            binding.rvInvoiceList.visibility = View.GONE
            binding.imgNada.visibility = View.VISIBLE
        } else {
            binding.rvInvoiceList.visibility = View.VISIBLE
            binding.imgNada.visibility = View.GONE
        }
        var adapter = AdaptadorFacturas(
            { i: Int, n: Int ->
                var bundle = Bundle();
                bundle.putInt("pos", i)
                parentFragmentManager.setFragmentResult("key", bundle)
                if (n == 0) {
                    findNavController().navigate(R.id.action_invoiceListFragment_to_invoiceDetailsFragment)
                } else if (n == 1) {
                    findNavController().navigate(R.id.action_invoiceListFragment_to_invoiceCreationFragment)
                }
            },
            { i: Int ->
                showDeleteConfirmationDialog(i)
            }
        )


        viewModel.allinvoice.observe(viewLifecycleOwner) {
            it.let { adapterInvoice.submitList(it) }
        }

        binding.fabInvoice.setOnClickListener {
            //findNavController().navigate()
            findNavController().navigate(R.id.action_invoiceListFragment_to_invoiceCreationFragment)
        }

        //viewModel.validate()
    }
    private fun setUpUserRecycler() {
        adapterInvoice = AdaptadorFacturas(
            { i: Int, n: Int ->
                var bundle = Bundle();
                bundle.putInt("pos", i)
                parentFragmentManager.setFragmentResult("key", bundle)
                if (n == 0) {
                    findNavController().navigate(R.id.action_invoiceListFragment_to_invoiceDetailsFragment)
                } else if (n == 1) {
                    findNavController().navigate(R.id.action_invoiceListFragment_to_invoiceCreationFragment)
                }
            },
            { i: Int ->
                showDeleteConfirmationDialog(i)
            }
        )

        with(binding.rvInvoiceList) {
            layoutManager = LinearLayoutManager(requireContext())
            //setHasFixedSize(true) Con list adapter hay que quitar esto
            //El recyckerview es dinamico ya que utilizamos listAdapter y se modifica en el número de elementos
            //Se debe quitar setHasFixedSize(true)
            this.adapter = adapterInvoice
        }
    }
    private fun onSuccess() {
        binding.imgNada.visibility = View.GONE
        binding.rvInvoiceList.visibility = View.VISIBLE
        Utils.showSnackBar(binding.root,"Lista Creada")
        println("------------------------------"+ viewModel.allinvoice.value)
    }

    private fun showDeleteConfirmationDialog(posicion: Int) {

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("¿Deseas eliminar esta factura?")
        builder.setPositiveButton("Eliminar") { _, _ ->
            viewModel.facturas.removeAt(posicion)
            if (viewModel.facturas.size < 1) {
                binding.rvInvoiceList.visibility = View.GONE
                binding.imgNada.visibility = View.VISIBLE
            } else {
                binding.rvInvoiceList.visibility = View.VISIBLE
                binding.imgNada.visibility = View.GONE
            }

            binding.rvInvoiceList.adapter?.notifyDataSetChanged()
        }

        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menulist, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {

            R.id.action_sort -> {
                //ordenar la lista del adapter aqui, con una funcion dentro del adapter, utilizar sortBy { it.propiedadPorLaQueOrdenar} y hacer el notifyDataSetChanged()
                viewModel.sortNombre()
                binding.rvInvoiceList.adapter?.notifyDataSetChanged()
                return true
            }

            R.id.action_refresh -> {
                viewModel.sortId()
                binding.rvInvoiceList.adapter?.notifyDataSetChanged()
                return true
                //viewmodel.sortId
            }

            else -> false

        }
    }
    override fun onStart() {
        super.onStart()
        viewModel.getInvoiceList()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}