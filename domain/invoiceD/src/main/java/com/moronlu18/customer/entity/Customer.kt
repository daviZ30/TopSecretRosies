package com.moronlu18.customer.entity

data class Cliente (
    val id:Int,
    val nombre: String, val apellidos: String, val email: String,
    val telefono: Long,
    val city: String,
    val direction: String)
