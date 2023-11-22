package com.moronlu18.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


data class Task(val titulo: String, val cliente: String, val fecha: String, val hora: String)

val tasks = listOf(
    Task("Crear Tarea", "Juan Luis Guerra Gennich", "13/04/2002", "00:00"),
    Task("Prueba List", "Alex Carnero Tapia", "09/11/2023", "00:00"),
    Task("Exponer proyecto", "David Zambrana", "10/11/2002", "10:00"),
    Task("Más pruebas", "Antonio Angel Salado Gomez", "01/01/2000", "00:01"),
    Task("Crear Nueva Tarea", "Juan Lucas", "21/11/2002", "22:22"),
    Task("Partida Sudoku", "Carnero", "29/11/2023", "08:15"),
    Task("Comprar patatas", "David ", "30/08/2022", "10:00"),
    Task("Más y más pruebas", "Antonio Salado Gomez", "07/01/2020", "00:01"),
)

class TaskListFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)

        val rvTaskList = view.findViewById<RecyclerView>(R.id.rvTaskList)
        rvTaskList.adapter = TaskListAdapter(tasks)
        rvTaskList.layoutManager = LinearLayoutManager(context)
        return view;
    }

}