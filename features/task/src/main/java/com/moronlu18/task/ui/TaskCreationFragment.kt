package com.moronlu18.task.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.moronlu18.customer.repository.ProviderCustomer
import com.moronlu18.task.entity.Task
import com.moronlu18.task.repository.ProviderTask
import com.moronlu18.taskFragment.databinding.FragmentTaskCreationBinding
import com.moronlu18.task.entity.TaskStatus
import com.moronlu18.task.entity.TaskType
import com.moronlu18.taskFragment.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class TaskCreationFragment : Fragment() {
    val tasks: MutableList<Task> = ProviderTask.taskExample
    val clientes = ProviderCustomer.datasetCustomer

    private var _binding: FragmentTaskCreationBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var names: MutableList<String> = mutableListOf()
        if (clientes.isEmpty()) {
            names.add("<No Existen Clientes>")
        } else {
            for (cliente in clientes) {
                names.add("${cliente.id}.-" + cliente.getFullName())
            }
        }
        binding.spTaskCreationCliente.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, names)
        binding.tieTaskCreationDateStart.setText(getCurrentDate())
        binding.btnTaskCreationAdd.setOnClickListener {
            val idTask = tasks.last().idTask + 1
            val title = binding.tieTaskCreationTitulo.text.toString() ?: "<Sin Titulo>"
            val desc = binding.tieTaskCreationDesc.text.toString() ?: ""
            val type = TaskType.private
            val status = TaskStatus.pending
            val createdDate = binding.tieTaskCreationDateStart.text.toString() ?: getCurrentDate()
            val endDate = binding.tieTaskCreationDateEnd.text.toString() ?: getCurrentDate()
            tasks.add(Task(idTask, 4, title, desc, "A", type, status, createdDate, endDate))
            var bundle = Bundle()
            parentFragmentManager.setFragmentResult("key",bundle)
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskCreationBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Función que devuelve la fecha actual y formateada
     */
    private fun getCurrentDate(): String {
        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
    }//Implementarlo más tarde para que todos puedan usarlo

}