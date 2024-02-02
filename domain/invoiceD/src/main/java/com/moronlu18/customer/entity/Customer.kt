package com.moronlu18.customer.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.moronlu18.invoice.converter.CustomerTypeConverter
import com.moronlu18.invoice.ui.firebase.Email
@Entity(tableName = "customer")
data class Customer(
    @PrimaryKey
    @TypeConverters(CustomerTypeConverter::class)
    val id: CustomerId,
    @NonNull
    val nombre: String,
    val apellidos: String,
    val email: Email,
    val telefono: String,
    val city: String,
    val direction: String
)/*: Identificator(id)*/ {
    public fun getFullName(): String {
        return this.nombre + " " + this.apellidos
    }
}
