package com.moronlu18.item.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    val itemList = listOf(staticItem, staticItem2, staticItem3, staticItem4)

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

        binding.rvItemList.adapter = ItemAdapter(itemList){
            findNavController().navigate(R.id.action_itemListFragment_to_itemDetailFragment)
        }
        binding.rvItemList.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabitemcreation.setOnClickListener {

            findNavController().navigate(R.id.action_itemListFragment_to_itemCreationFragment)
        }



    }
}