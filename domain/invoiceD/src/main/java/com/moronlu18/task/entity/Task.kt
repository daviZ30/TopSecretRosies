package com.moronlu18.task.entity

import com.moronlu18.customer.entity.Cliente
import com.moronlu18.invoice.entity.Identificator

data class Task(
    val idTask: Int,
    var customer: Cliente,
    var title: String,
    var description : String,
    var type : TaskType,
    var status: TaskStatus,
    var createdDate: String, //Instant
    var endDate: String //Instant
) : Identificator(idTask) {

}