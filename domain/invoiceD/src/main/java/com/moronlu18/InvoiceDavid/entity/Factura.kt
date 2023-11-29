package com.moronlu18.invoice.entity

import com.moronlu18.customer.entity.Cliente

data class Factura(
    val Cliente: Cliente,
    val FeEmision: String,
    val FeVencimiento: String,
    val Articulos: List<Articulo>,

    )
