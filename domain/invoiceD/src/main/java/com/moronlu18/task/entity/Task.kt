package com.moronlu18.task.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.moronlu18.customer.entity.Cliente
import com.moronlu18.invoice.converter.InvoiceIdTypeConverter
import com.moronlu18.invoice.entity.UniqueId
import org.jetbrains.annotations.NotNull

@Entity(
    tableName = "task", foreignKeys =
    [ForeignKey(
        entity = Cliente::class, parentColumns = arrayOf("CustomerId"), childColumns = arrayOf("customer"),
        onDelete = ForeignKey.RESTRICT, onUpdate = ForeignKey.CASCADE
    )]
)
data class Task(
    @PrimaryKey
    @TypeConverters(InvoiceIdTypeConverter::class)
    val idTask: TaskId,
    @NotNull
    var customer: Cliente,
    @NotNull
    var title: String,
    var description: String,
    @NotNull
    var type: TaskType,
    @NotNull
    var status: TaskStatus,
    @NotNull
    var createdDate: String, //Instant
    var endDate: String //Instant
)

data class TaskId(override val value: Int) : UniqueId(value)