package com.moronlu18.item.repository

import com.moronlu18.item.entity.item
import com.moronlu18.item.entity.itemType

 class ItemRepository private constructor() {
    companion object {
        private val itemList = mutableListOf(
            item(1, "Lápiz", 3.55, itemType.PRODUCT, "Lápiz pequeño", false, 0.02),
            item(2, "Goma", 1.23, itemType.PRODUCT, "Goma cuadrada", false, 0.02),
            item(3, "Bolígrafo", 2.23, itemType.PRODUCT, "Bolígrafo rojo", false, 0.02),
            item(4, "Sacapuntas", 1.63, itemType.PRODUCT, "Sacapuntas gris", false, 0.02)
        )

        fun getItemList(): List<item> {
            return itemList
        }
     fun getName(id:Int):String?{
         itemList.forEach{
             if(it.id == id){
                 return it.name
             }
         }
         return null
     }

        fun addItem(item: item) {
            itemList.add(item)
        }

        fun removeItem(item: item) {
            itemList.remove(item)
        }

        fun updateItem(updatedItem: item) {
            val existingItem = itemList.find { it.id == updatedItem.id }

            existingItem?.apply {
                name = updatedItem.name
                rate = updatedItem.rate
                type = updatedItem.type
                description = updatedItem.description
                isTaxable = updatedItem.isTaxable
            }
        }


    }

}