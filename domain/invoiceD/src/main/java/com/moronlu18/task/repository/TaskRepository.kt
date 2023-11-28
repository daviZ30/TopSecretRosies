package com.moronlu18.task.repository

import com.moronlu18.task.entity.Task
import com.moronlu18.task.entity.TaskStatus
import com.moronlu18.task.entity.TaskType

class TaskRepository {
    //Ejmplos de Task
    private var idTask : Int = 1
    val taskExample : List<Task> =listOf(
        Task(idTask++,1,"Crear Tarea", "Crear layout tareas","Juan Luis Guerra Gennich", TaskType.private, TaskStatus.pending,"13/04/2002", "00:00"),
        Task(idTask++,2,"Prueba List","Probar listas", "Alex Carnero Tapia", TaskType.private, TaskStatus.pending, "09/11/2023", "00:00"),
        Task(idTask++,3,"Exponer proyecto", "Primera exposición del proyecto","David Zambrana", TaskType.private, TaskStatus.pending, "10/11/2002", "10:00"),
        Task(idTask++,4,"Más pruebas", "Probando el proyecto","Antonio Angel Salado Gomez", TaskType.private, TaskStatus.pending, "01/01/2000", "00:01"),
        Task(idTask++,5,"Crear Nueva Tarea", "Crear primera tarea","Juan Lucas",  TaskType.private, TaskStatus.pending,"21/11/2003", "22:22"),
        Task(idTask++,6,"Partida Sudoku", "Partidita chill","Carnero", TaskType.private, TaskStatus.pending, "29/11/2023", "08:15"),
        Task(idTask++,7,"Comprar patatas", "Comprar patatas a 20€ el kilo","David ", TaskType.private, TaskStatus.pending, "30/08/2022", "10:00"),
        Task(idTask++,8,"Más y más pruebas","Probando, probando, probando", "Antonio Salado Gomez", TaskType.private, TaskStatus.pending, "07/01/2020", "00:01"),
        Task(idTask++,9,"Por que si", "Nombre y apellidos más comúnes","Mohamed Wang Smith",  TaskType.private, TaskStatus.pending,"07/12/2023", "10:01"),
    )
}