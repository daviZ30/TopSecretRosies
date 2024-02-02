package com.moronlu18.task.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.moronlu18.customer.entity.Customer
import com.moronlu18.invoice.converter.TaskIdConverter
import com.moronlu18.invoice.converter.TaskStringLongConverter
import com.moronlu18.invoice.entity.UniqueId
import org.jetbrains.annotations.NotNull

@Entity(
    tableName = "task", foreignKeys =
    [ForeignKey(
        entity = Customer::class, parentColumns = arrayOf("CustomerId"), childColumns = arrayOf("customer"),
        onDelete = ForeignKey.RESTRICT, onUpdate = ForeignKey.CASCADE
    )]
)
data class Task(
    @PrimaryKey
    @TypeConverters(TaskIdConverter::class)
    val idTask: TaskId,
    @NotNull
    var customer: Customer,
    @NotNull
    var title: String,
    var description: String,
    @NotNull
    var type: TaskType,
    @NotNull
    var status: TaskStatus,
    @NotNull
    @TypeConverters(TaskStringLongConverter::class)
    var createdDate: String, //Instant
    @TypeConverters(TaskStringLongConverter::class)
    var endDate: String //Instant
)

data class TaskId(override val value: Int) : UniqueId(value)