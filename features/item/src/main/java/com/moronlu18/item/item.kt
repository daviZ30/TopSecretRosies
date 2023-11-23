package com.moronlu18.item

data class item(
    val id: Int,
    val name: String,
    val rate: Double,
    val type: itemType,
    val description: String,
    val isTaxable: Boolean
)

enum class itemType {
    PRODUCT,
    SERVICE
}