package com.moronlu18.invoice.entity

import com.moronlu18.customer.entity.Cliente
import com.moronlu18.item.entity.item
import java.util.Date


data class Factura(
    val id : Int,
    val Cliente: Cliente,
    val FeEmision: String,
    val FeVencimiento: String,
    val Articulos: MutableList<item>,

    )
