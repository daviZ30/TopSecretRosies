package com.moronlu18.InvoiceDavid.entity

import androidx.room.Entity
import com.moronlu18.item.entity.ItemId
import com.moronlu18.item.entity.item
import com.moronlu18.item.repository.ItemRepository

//@Entity
data class LineaItem(
    val id_item: Int,
    val id_invoice: Int,
    val cantidad: Int,
    val precio: Double,
    val iva: Double
) : Comparable<LineaItem> {
    override fun compareTo(other: LineaItem): Int {
        return id_item.compareTo(other.id_item)
    }

    companion object {

        fun getName(id:Int):String?{
            return ItemRepository.getName(ItemId(id))
        }
        fun ToLineaItem(lista: MutableList<item>, id_invoice: Int): MutableList<LineaItem> {
            val newlist: MutableList<LineaItem> = mutableListOf()
            lista.forEach {
                newlist.add(
                    LineaItem(
                        it.id.value,
                        id_invoice,
                        lista.count { element -> element.id == it.id },
                        it.rate,
                        it.Iva
                    )
                )
            }
            return newlist
        }
        /*fun ToItem(lista:MutableList<LineaItem>): MutableList<item>{
            val newlist: MutableList<item> = mutableListOf()
            lista.forEach{
                newlist.add(ItemRepository.getInstance().getItem(it.id_item)!!)
            }
            return newlist
        }*/
    }

}