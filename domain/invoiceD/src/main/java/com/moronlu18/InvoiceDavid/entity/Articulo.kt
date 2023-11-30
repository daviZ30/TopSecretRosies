package com.moronlu18.invoice.entity

data class Articulo(val nombre:String,val precio:Double){
    override fun toString(): String {
        return "$nombre - $precio€"
    }
}