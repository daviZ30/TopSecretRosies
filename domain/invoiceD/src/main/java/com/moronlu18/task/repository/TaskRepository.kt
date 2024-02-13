package com.moronlu18.task.repository

import com.moronlu18.invoice.InvoiceDatabase
import com.moronlu18.task.entity.Task

class TaskRepository {
    fun insert(task: Task) {
        InvoiceDatabase.getInstance().taskDao().insert(task)
    }

    companion object {
        fun insert(task: Task) {
            InvoiceDatabase.getInstance().taskDao()?.insert(task)
        }
    }
}