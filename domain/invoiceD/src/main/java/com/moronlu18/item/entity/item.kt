package com.moronlu18.item.entity

class item(
    val id: Int,
    val name: String,
    val rate: Double,
    val type: itemType,
    val description: String,
    val isTaxable: Boolean
){
    override fun toString(): String {
        return "$name - $rate€"
    }
}

enum class itemType {
    PRODUCT,
    SERVICE
}