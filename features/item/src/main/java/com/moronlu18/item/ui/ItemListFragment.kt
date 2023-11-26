package com.moronlu18.item.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moronlu18.item.adapter.ItemAdapter
import com.moronlu18.item.item
import com.moronlu18.item.itemType
import com.moronlu18.itemcreation.R
import com.moronlu18.itemcreation.databinding.FragmentItemListBinding


class ItemListFragment : Fragment() {

    private val staticItem: item = item(
        id = 1,
        name = "Lápiz",
        rate = 3.55,
        type = itemType.PRODUCT,
        description = "Lápiz pequeño",
        isTaxable = false
    )

    private val staticItem2: item = item(
        id = 2,
        name = "Goma",
        rate = 1.23,
        type = itemType.PRODUCT,
        description = "Goma cuadrada",
        isTaxable = false
    )

    private val staticItem3: item = item(
        id = 3,
        name = "Boligrafo",
        rate = 2.23,
        type = itemType.PRODUCT,
        description = "Boligrado rojo",
        isTaxable = false
    )

    private val staticItem4: item = item(
        id = 4,
        name = "Sacapuntas",
        rate = 1.63,
        type = itemType.PRODUCT,
        description = "Sacapuntas gris",
        isTaxable = false
    )



    var itemList = mutableListOf(staticItem, staticItem2, staticItem3, staticItem4)


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
            itemList,
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabitemcreation.setOnClickListener {

            findNavController().navigate(R.id.action_itemListFragment_to_itemCreationFragment)
        }
    }

    private fun showDeleteConfirmationDialog(item: item) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Eliminar artículo")
            .setMessage("¿Estás seguro de que quieres eliminar este artículo?")
            .setPositiveButton("Eliminar") { _, _ ->

                itemList.remove(item)

                binding.rvItemList.adapter?.notifyDataSetChanged()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}


