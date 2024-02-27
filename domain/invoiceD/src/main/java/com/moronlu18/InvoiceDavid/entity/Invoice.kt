package com.moronlu18.invoice.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.moronlu18.InvoiceDavid.entity.InvoiceId
import com.moronlu18.InvoiceDavid.entity.InvoiceStatus
import com.moronlu18.InvoiceDavid.entity.LineaItem
import com.moronlu18.customer.entity.Customer
import com.moronlu18.customer.entity.CustomerId
import com.moronlu18.invoice.converter.CustomerIDTypeConverter
import com.moronlu18.invoice.converter.InvoiceIdTypeConverter
import com.moronlu18.invoice.converter.InvoiceInstantLongConverter
import com.moronlu18.invoice.converter.InvoiceStatusConverter
import java.io.Serializable
import java.time.Instant

@Entity(
    tableName = "invoice", foreignKeys = [ForeignKey(
        entity = Customer::class,
        parentColumns = arrayOf("id"), childColumns = arrayOf("idCliente"),
        onDelete = ForeignKey.RESTRICT, onUpdate = ForeignKey.CASCADE
    )]
)
data class Invoice(
    @PrimaryKey
    @TypeConverters(InvoiceIdTypeConverter::class)
    val id: InvoiceId,
    @TypeConverters(CustomerIDTypeConverter::class)
    val idCliente: CustomerId,
    @TypeConverters(InvoiceInstantLongConverter::class)
    val FeEmision: Instant,
    @TypeConverters(InvoiceInstantLongConverter::class)
    val FeVencimiento: Instant,
    @Ignore
    var Articulos: MutableList<LineaItem>,
    @TypeConverters(InvoiceStatusConverter::class)
    val Estado: InvoiceStatus
) : Serializable {
    constructor(
        id: InvoiceId,
        idCliente: CustomerId,
        FeEmision: Instant,
        FeVencimiento: Instant,
        Estado: InvoiceStatus
    ) : this(id, idCliente, FeEmision, FeVencimiento, mutableListOf(), Estado)

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

