package com.moronlu18.task.repository

import com.moronlu18.invoice.InvoiceDatabase
import com.moronlu18.task.entity.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository {
    companion object {
        fun insertTask(task: Task) {
            InvoiceDatabase.getInstance().taskDao().insert(task)
        }
        fun selectAllTaskList() : Flow<List<Task>> {
            return InvoiceDatabase.getInstance().taskDao().selectAll()
        }
        fun selectAllTaskListRAW() : List<Task> {
            return InvoiceDatabase.getInstance().taskDao().selectAllRAW()
        }
        fun deleteTask(task: Task){
            InvoiceDatabase.getInstance().taskDao().delete(task)
        }
        fun updateTask(task : Task){

        }
    }
}