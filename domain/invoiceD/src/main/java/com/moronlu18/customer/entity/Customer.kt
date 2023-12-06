package com.moronlu18.customer.entity

import com.moronlu18.invoice.ui.firebase.Email

data class Cliente(
    val id: Int,
    val nombre: String,
    val apellidos: String,
    val email: Email,
    val telefono: String,
    val city: String,
    val direction: String
) {
    public fun getFullName(): String {
        return this.nombre + " " + this.apellidos
    }
}
