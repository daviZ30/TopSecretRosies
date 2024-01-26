 package com.moronlu18.item.entity

 import androidx.room.Entity
 import androidx.room.ForeignKey
 import androidx.room.PrimaryKey

 /**
 * Al utilizar data class se implementa de forma automatica el metodo equals toString, hashcode, copy
 * teniendo en cuenta las propiedades declarasas en el constructor primario
 */
@Entity(tableName = "item", foreignKeys =[
    ForeignKey(entity = itemType::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("itemtype"),
        onDelete = ForeignKey.RESTRICT,
        onUpdate = ForeignKey.CASCADE)
])
data class item(
    @PrimaryKey
    val id: Int,
    var name: String,
    var rate: Double,
    var type: itemType,
    var description: String,
    var isTaxable: Boolean,
    var Iva: Double
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