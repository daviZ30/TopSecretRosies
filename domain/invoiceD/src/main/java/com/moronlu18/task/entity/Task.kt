package com.moronlu18.task.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.moronlu18.customer.entity.Cliente
import com.moronlu18.invoice.entity.UniqueId

@Entity(
    tableName = "task", foreignKeys =
    [ForeignKey(
        entity = Cliente::class, parentColumns = arrayOf("id"), childColumns = arrayOf("customer"),
        onDelete = ForeignKey.RESTRICT, onUpdate = ForeignKey.CASCADE
    )]
)
data class Task(
    @PrimaryKey
    val idTask: TaskId,
    var customer: Cliente,
    var title: String,
    var description: String,
    var type: TaskType,
    var status: TaskStatus,
    var createdDate: String, //Instant
    var endDate: String //Instant
)

data class TaskId(override val value: Int) : UniqueId(value)