package com.moronlu18.task.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.moronlu18.task.data.model.Task
import com.moronlu18.task.data.model.TaskStatus
import com.moronlu18.task.data.model.TaskType
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
        var idTask : Int = 1
        val tasks = listOf(
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
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        binding.rvTaskList.adapter = TaskListAdapter(tasks){
            findNavController().navigate(R.id.action_taskListFragment_to_taskDetailFragment)
        }
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

