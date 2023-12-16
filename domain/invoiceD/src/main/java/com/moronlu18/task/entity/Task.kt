package com.moronlu18.task.entity

data class Task(
    val idTask: Int,
    var customerId : Int,
    var title: String,
    var description : String,
    var nameCustomer: String,
    var type : TaskType,
    var status: TaskStatus,
    var createdDate: String, //Instant
    var endDate: String //Instant
)
