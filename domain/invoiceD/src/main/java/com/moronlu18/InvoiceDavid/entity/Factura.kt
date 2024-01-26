package com.moronlu18.invoice.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.moronlu18.InvoiceDavid.converter.InvoiceIdTypeConverter
import com.moronlu18.InvoiceDavid.entity.InvoiceId
import com.moronlu18.InvoiceDavid.entity.InvoiceStatus
import com.moronlu18.InvoiceDavid.entity.LineaItem
import java.time.Instant

@Entity
data class Factura(
    @PrimaryKey
    @TypeConverters(InvoiceIdTypeConverter::class)
    val id: InvoiceId,
    val idCliente: Int,
    val FeEmision: Instant,
    val FeVencimiento: Instant,
    val Articulos: MutableList<LineaItem>,
    val Estado: InvoiceStatus

)/*:Identificator(id)*/ {
    /*public fun CantidadArticulos(): Map<LineaItem,Int> {
        val DiferencesItem: MutableMap<LineaItem, Int> = mutableMapOf()
        var modificado = false;
         Articulos.forEach{
            println("numero de articulos " + Articulos.size)

            DiferencesItem.forEach { k, v ->
                if(k.compareTo(it) == 0){
                    println("ifffffffff")
                    var n:Int? = DiferencesItem[it]
                     if(n == null){
                         println("null")
                         DiferencesItem[it] = 1
                     }else{
                         println("!= nuñ")
                         DiferencesItem[it] = n++
                         modificado = true
                     }

            }
        }
             if(!modificado){
                 DiferencesItem[it] = 1
             }else{
                 modificado = false
             }

    }
        return DiferencesItem
}*/
}

