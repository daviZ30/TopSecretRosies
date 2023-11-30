package com.moronlu18.task.repository

import com.moronlu18.task.entity.Task
import com.moronlu18.task.entity.TaskStatus
import com.moronlu18.task.entity.TaskType
import com.moronlu18.customer.entity.Cliente
import com.moronlu18.customer.repository.ProviderCustomer

class ProviderTask {
    //Ejmplos de Task
    private var aux : Int = 0
    private val customer : MutableList<Cliente> = ProviderCustomer.datasetCustomer
    val taskExample : MutableList<Task> = arrayListOf(
        Task(aux + 1,customer[aux].id,"Crear Tarea", "Crear layout tareas",customer[aux++].getFullName(), TaskType.private, TaskStatus.pending,"13/04/2002", "13/04/2024"),
        Task(aux + 1,customer[aux].id,"Prueba List","Probar listas", customer[aux++].getFullName(), TaskType.private, TaskStatus.pending, "09/11/2023", "31/12/2023"),
        Task(aux + 1,customer[aux].id,"Exponer proyecto", "Exposición del proyecto",customer[aux++].getFullName(), TaskType.private, TaskStatus.pending, "10/11/2023", "10/11/2023"),

        /*Task(idTask++,cliente[0].id,"Más pruebas", "Probando el proyecto","Antonio Angel Salado Gomez", TaskType.private, TaskStatus.pending, "01/01/2000", "00:01"),
        Task(idTask++,cliente[1].id,"Crear Nueva Tarea", "Crear primera tarea","Juan Lucas",  TaskType.private, TaskStatus.pending,"21/11/2003", "22:22"),
        Task(idTask++,cliente[2].id,"Partida Sudoku", "Partidita chill","Carnero", TaskType.private, TaskStatus.pending, "29/11/2023", "08:15"),
        Task(idTask++,cliente[0].id,"Comprar patatas", "Comprar patatas a 20€ el kilo","David ", TaskType.private, TaskStatus.pending, "30/08/2022", "10:00"),
        Task(idTask++,cliente[1].id,"Más y más pruebas","Probando, probando, probando", "Antonio Salado Gomez", TaskType.private, TaskStatus.pending, "07/01/2020", "00:01"),
        Task(idTask++,cliente[2].id,"Por que si", "Nombre y apellidos más comúnes","Mohamed Wang Smith",  TaskType.private, TaskStatus.pending,"07/12/2023", "10:01"),*/
    )
}