package com.moronlu18.item.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.moronlu18.item.adapter.ItemAdapter
import com.moronlu18.item.entity.item
import com.moronlu18.item.entity.itemType
import com.moronlu18.itemcreation.R
import com.moronlu18.item.repository.ItemRepository
import com.moronlu18.item.usecase.ItemViewModel

import com.moronlu18.itemcreation.databinding.FragmentItemListBinding


class ItemListFragment : Fragment() {

    private val itemRepository = ItemRepository.getInstance()

    private lateinit var itemViewModel: ItemViewModel

    private var _binding: FragmentItemListBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

                findNavController().navigate(
                    R.id.action_itemListFragment_to_itemDetailFragment,
                    bundle
                )
            },
            {
                showDeleteConfirmationDialog(it)
            }
        )

        binding.rvItemList.adapter = adapter
        binding.rvItemList.layoutManager = LinearLayoutManager(context)

        itemViewModel = ViewModelProvider(requireActivity()).get(ItemViewModel::class.java)


        itemViewModel.newItem.observe(viewLifecycleOwner) { newItem ->
            if (newItem != null) {
                itemRepository.addItem(newItem)
                binding.rvItemList.adapter?.notifyDataSetChanged()
                itemViewModel.clearNewItem()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabitemcreation.setOnClickListener {

            findNavController().navigate(R.id.action_itemListFragment_to_itemCreationFragment)
        }
    }

    private fun showDeleteConfirmationDialog(item:item) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Eliminar artículo")
            .setMessage("¿Estás seguro de que quieres eliminar este artículo?")
            .setPositiveButton("Eliminar") { _, _ ->

                itemRepository.removeItem(item)

                binding.rvItemList.adapter?.notifyDataSetChanged()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}


