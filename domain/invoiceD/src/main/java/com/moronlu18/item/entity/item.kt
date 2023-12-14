package com.moronlu18.item.entity

/**
 * Al utilizar data class se implementa de forma automatica el metodo equals toString, hashcode, copy
 * teniendo en cuenta las propiedades declarasas en el constructor primario
 */
data class item(
    val id: Int,
    var name: String,
    var rate: Double,
    var type: itemType,
    var description: String,
    var isTaxable: Boolean
) : Comparable<item>{
    override fun compareTo(other: item): Int {
        return name.compareTo(other.name)
    }

    override fun toString(): String {
        return "$name - $rate€"
    }
}

enum class itemType {
    PRODUCT,
    SERVICE
}