package com.moronlu18.item.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.moronlu18.invoice.MainActivity
import com.moronlu18.invoice.Repository.ProviderInvoice
import com.moronlu18.item.adapter.ItemAdapter
import com.moronlu18.item.entity.item
import com.moronlu18.itemcreation.R
import com.moronlu18.item.repository.ItemRepository
import com.moronlu18.item.usecase.ItemViewModel

import com.moronlu18.itemcreation.databinding.FragmentItemListBinding


class ItemListFragment : Fragment(), MenuProvider {

    private val itemRepository = ItemRepository.getInstance()

    private lateinit var itemViewModel: ItemViewModel

    private var _binding: FragmentItemListBinding? = null
    private val binding
        get() = _binding!!

    private var isSortedByName = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentItemListBinding.inflate(inflater, container, false)


        val adapter = ItemAdapter(
            itemRepository.getItemList(),
            { item ->
                val bundle = Bundle().apply {
                    putInt("id", item.id)
                    putString("name", item.name)
                    putDouble("rate", item.rate)
                    putSerializable("type", item.type)
                    putString("description", item.description)
                    putBoolean("isTaxable", item.isTaxable)
                }

                itemRepository.getItemList().sortedDescending()

                findNavController().navigate(
                    R.id.action_itemListFragment_to_itemDetailFragment,
                    bundle
                )
            },
            {
                showDeleteConfirmationDialog(it)
            },
            {  item ->
                val bundle = Bundle().apply {
                    putInt("id", item.id)
                    putString("name", item.name)
                    putDouble("rate", item.rate)
                    putSerializable("type", item.type)
                    putString("description", item.description)
                    putBoolean("isTaxable", item.isTaxable)
                }

                findNavController().navigate(
                    R.id.action_itemListFragment_to_itemCreationFragment2,
                    bundle
                )
            }
        )



        binding.rvItemList.adapter = adapter
        binding.rvItemList.layoutManager = LinearLayoutManager(context)

        itemViewModel = ViewModelProvider(requireActivity()).get(ItemViewModel::class.java)


        itemViewModel.newItem.observe(viewLifecycleOwner) { newItem ->
            if (newItem != null) {
                itemRepository.addItem(newItem)
                sortItemList(itemRepository.getItemList())
                itemViewModel.clearNewItem()
            }
        }



        binding.rvItemList.adapter = adapter
        binding.rvItemList.layoutManager = LinearLayoutManager(context)


        val initialList = itemRepository.getItemList()
        sortItemList(initialList)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Funcion que personaliza el fav
        setUpFav()
        //Funcion que personaliza el menu de la toolbar
        setUpToolbar()


        binding.fabitemcreation.setOnClickListener {

            findNavController().navigate(R.id.action_itemListFragment_to_itemCreationFragment)
        }
    }

    /**
     * Esta funcion personaliza el comportamiento del boton flotante de la activity
     */
    private fun setUpFav() {
        /*val fab = (requireActivity() as? MainActivity)?.fab?.apply {
            visibility = View.VISIBLE
            setOnClickListener { view ->
                Snackbar.make(view,"soy el fragment", Snackbar.LENGTH_LONG).show()
            }
        }*/

      //Opcion 1
      //val fab =  (requireActivity() as? MainActivity)?.fab
        //fab?.visibility=View.VISIBLE
        //fab?.setOnClickListener { view ->
            // aqui la accion del listener
        //    Snackbar.make(view,"soy el fragment", Snackbar.LENGTH_LONG).show()
       // }
    }

    /**
     * Esta funcion personaliza el comportamiento de la toolbat de la activity
     */
    private fun setUpToolbar() {
        //Modismo Apply de kotlin
        (requireActivity() as? MainActivity)?.toolbar?.apply {
            visibility = View.VISIBLE
           val menuhost : MenuHost = requireActivity()
          // menuhost.addMenuProvider(this,viewLifecycleOwner,Lifecycle.State.RESUMED)
        }
    }

    /**
     * Se añade
     */
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_list_items, menu)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Log.d("TAG", "onCreateOptionsMenu called")
        inflater.inflate(R.menu.menu_list_items, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return onMenuItemSelected(item)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_sort -> {
                isSortedByName = !isSortedByName
                sortItemList(itemRepository.getItemList())
                true
            }
            else -> false
        }
    }



    private fun showDeleteConfirmationDialog(item: item) {

        val isItemInInvoice = isItemInAnyInvoice(item)

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Eliminar artículo")

        if (isItemInInvoice) {
            builder.setMessage("Este artículo está presente en una factura. ¿Estás seguro de que quieres eliminarlo?")
        } else {
            builder.setMessage("¿Estás seguro de que quieres eliminar este artículo?")
        }

        builder.setPositiveButton("Eliminar") { _, _ ->

            if (!isItemInInvoice) {
                itemRepository.removeItem(item)
                binding.rvItemList.adapter?.notifyDataSetChanged()
            } else {

                Toast.makeText(
                    requireContext(),
                    "No se puede eliminar el artículo, ya está en una factura.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun isItemInAnyInvoice(item: item): Boolean {
        val invoicesWithItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ProviderInvoice.datasetFactura.filter { factura ->
                factura.Articulos.any { it.id == item.id }
            }
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        return invoicesWithItem.isNotEmpty()
    }

    private fun sortItemList(itemList: List<item>) {
        val sortedList = if (isSortedByName) {
            itemList.sortedBy { it.name }
        } else {
            itemList
        }
        updateAdapter(sortedList)
    }

    private fun updateAdapter(updatedList: List<item>) {
        val adapter = binding.rvItemList.adapter as? ItemAdapter
        adapter?.updateItemList(updatedList)
        adapter?.notifyDataSetChanged()
    }


}


