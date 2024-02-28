package com.moronlu18.task.repository

import Resources
import com.moronlu18.invoice.InvoiceDatabase
import com.moronlu18.task.entity.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepository {
    companion object {
        fun insertTask(task: Task) {
            try {
                InvoiceDatabase.getInstance().taskDao().insert(task)
                Resources.Success(task)
            } catch (e: Exception) {
                Resources.Error(e)
            }
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
            InvoiceDatabase.getInstance().taskDao().update(task)
        }
        fun selectAllTaskListSorted() : Flow<List<Task>> {
            return InvoiceDatabase.getInstance().taskDao().selectAll().map { tasks ->
                tasks.sortedBy { it.customer.nombre } // Ordenar la lista por el nombre del cliente
            }
        }

        fun orderByCustomer() : List<Task>{
            return InvoiceDatabase.getInstance().taskDao().sortByCustomer()
        }
    }
}