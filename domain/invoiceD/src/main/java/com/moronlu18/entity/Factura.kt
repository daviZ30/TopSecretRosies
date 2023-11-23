package com.moronlu18.entity

data class Factura(
    val Cliente: String,
    val Email: String,
    val Telefono: Int,
    val FeEmision: String,
    val FeVencimiento: String,
    val Articulos: List<Articulo>,

)
