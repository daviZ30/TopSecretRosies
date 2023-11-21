package com.moronlu18.entity

import com.moronlu18.entity.Articulo

data class Factura(val Cliente:String, val FeEmision:String, val FeVencimiento:String, val Articulos:List<Articulo>, val total:Double)
