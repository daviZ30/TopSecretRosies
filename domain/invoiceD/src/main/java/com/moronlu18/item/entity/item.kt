package com.moronlu18.item.entity

class item(
    val id: Int,
    var name: String,
    var rate: Double,
    var type: itemType,
    var description: String,
    var isTaxable: Boolean
){
    override fun toString(): String {
        return "$name - $rate€"
    }
}

enum class itemType {
    PRODUCT,
    SERVICE
}