package com.moronlu18.InvoiceDavid.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.moronlu18.invoice.entity.Invoice
import com.moronlu18.item.entity.ItemId
import com.moronlu18.item.entity.item
import com.moronlu18.item.repository.ItemRepository

@Entity(tableName = "LineaItem", foreignKeys = [ForeignKey(
    entity = Invoice::class,
    parentColumns = arrayOf("id"), childColumns = arrayOf("id_invoice"),
    onDelete = ForeignKey.RESTRICT, onUpdate = ForeignKey.CASCADE
)], primaryKeys = [ "id_item","id_invoice" ] )

data class LineaItem(
    val id_item: Int,
    val id_invoice: Int,
    val cantidad: Int,
    @NonNull
    val precio: Double,
    @NonNull
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