package com.moronlu18.task.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moronlu18.task.Task
import com.moronlu18.taskFragment.R
import com.moronlu18.task.adapter.TaskListAdapter
import com.moronlu18.taskFragment.databinding.FragmentTaskListBinding


class TaskListFragment : Fragment() {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)

        //Tareas de ejemplo
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
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        binding.rvTaskList.adapter = TaskListAdapter(tasks)
        binding.rvTaskList.layoutManager = LinearLayoutManager(context)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabTaskList.setOnClickListener {
            findNavController().navigate(R.id.action_taskListFragment_to_taskCreationFragment)
        }
    }

    /*override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }*/
}

