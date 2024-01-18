package com.moronlu18.customer.entity

import com.moronlu18.invoice.entity.Identificator
import com.moronlu18.invoice.ui.firebase.Email

data class Cliente(
    val id: Int,
    val nombre: String,
    val apellidos: String,
    val email: Email,
    val telefono: String,
    val city: String,
    val direction: String
): Identificator(id) {
    public fun getFullName(): String {
        return this.nombre + " " + this.apellidos
    }
}
