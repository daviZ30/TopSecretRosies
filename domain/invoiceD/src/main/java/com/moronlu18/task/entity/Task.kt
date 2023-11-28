package com.moronlu18.task.entity

data class Task(
    val idTask: Int,
    val customerId : Int,
    val title: String,
    val description : String,
    val cliente: String,// Borrar
    val type : TaskType,
    val state: TaskStatus,
    val createdDate: String, //Instant
    val endDate: String //Instant y cambiar datos
)
