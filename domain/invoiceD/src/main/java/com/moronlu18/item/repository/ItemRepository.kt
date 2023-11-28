package com.moronlu18.item.repository

import com.moronlu18.item.entity.item
import com.moronlu18.item.entity.itemType

class ItemRepository {
    private val itemList = mutableListOf(
        item(1, "Lápiz", 3.55, itemType.PRODUCT, "Lápiz pequeño", false),
        item(2, "Goma", 1.23, itemType.PRODUCT, "Goma cuadrada", false),
        item(3, "Bolígrafo", 2.23, itemType.PRODUCT, "Bolígrafo rojo", false),
        item(4, "Sacapuntas", 1.63, itemType.PRODUCT, "Sacapuntas gris", false)
    )


    fun getItemList(): List<item> {
        return itemList
    }

    fun addItem(item: item) {
        itemList.add(item)
    }

    fun removeItem(item: item) {
        itemList.remove(item)
    }
}
