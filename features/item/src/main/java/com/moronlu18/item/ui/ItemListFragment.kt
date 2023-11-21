package com.moronlu18.item.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moronlu18.item.adapter.ItemAdapter
import com.moronlu18.item.item
import com.moronlu18.item.itemType
import com.moronlu18.itemcreation.R


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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvItemList)


        val adapter = ItemAdapter(itemList)
        recyclerView.adapter = adapter


        recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }


}