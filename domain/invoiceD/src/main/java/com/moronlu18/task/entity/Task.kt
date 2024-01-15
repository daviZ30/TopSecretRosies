package com.moronlu18.task.entity

import com.moronlu18.customer.entity.Cliente

data class Task(
    val idTask: Int,
    var customer: Cliente,
    var title: String,
    var description : String,
    var type : TaskType,
    var status: TaskStatus,
    var createdDate: String, //Instant
    var endDate: String //Instant
) : Comparable<Task> {
    override fun compareTo(other: Task): Int {
        return idTask.compareTo(other.idTask)
    }
}