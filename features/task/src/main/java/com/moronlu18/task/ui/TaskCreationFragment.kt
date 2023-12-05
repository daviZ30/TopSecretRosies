package com.moronlu18.task.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.moronlu18.customer.repository.ProviderCustomer
import com.moronlu18.task.calendar.CalendarInvoice
import com.moronlu18.task.calendar.DatePickerFragment
import com.moronlu18.task.entity.Task
import com.moronlu18.task.repository.ProviderTask
import com.moronlu18.taskFragment.databinding.FragmentTaskCreationBinding
import com.moronlu18.task.entity.TaskStatus
import com.moronlu18.task.entity.TaskType


class TaskCreationFragment : Fragment() {
    val tasks: MutableList<Task> = ProviderTask.taskExample
    val clientes = ProviderCustomer.datasetCustomer
    val c = CalendarInvoice()

    private var _binding: FragmentTaskCreationBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    fun showDatePickerDialog(listener : (day: Int, month : Int, year: Int) -> Unit){
        val datePicker = DatePickerFragment{day: Int, month : Int, year: Int -> listener(day,month,year)}
        datePicker.show(parentFragmentManager, "datePicker")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var names: MutableList<String> = mutableListOf()

        //        var names: Map<Int,String> = mutableListOf()
        if (clientes.isEmpty()) {
            names.add("<No Existen Clientes>")
        } else {
            for (cliente in clientes) {
                names.add("${cliente.id}.-" + cliente.getFullName())
            }
        }
        binding.spTaskCreationCliente.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, names)
        binding.tieTaskCreationDateStart.setText(c.getCurrentDate())

        binding.tieTaskCreationDateStart.setOnClickListener {
            c.showDatePickerDialog(parentFragmentManager) { day, month, year ->
                binding.tieTaskCreationDateStart.setText("$day/${month + 1}/$year")
            }
        }

        binding.tieTaskCreationDateEnd.setOnClickListener {
            c.showDatePickerDialog(parentFragmentManager) { day, month, year ->
                binding.tieTaskCreationDateEnd.setText("$day/${month + 1}/$year")
            }
        }

        binding.btnTaskCreationAdd.setOnClickListener {
            val idTask = tasks.last().idTask + 1
            val idCliente =  4 // binding.spTaskCreationCliente.selectedItem
            val title = binding.tieTaskCreationTitulo.text.toString() ?: "<Sin Titulo>"
            val desc = binding.tieTaskCreationDesc.text.toString() ?: ""
            val type = TaskType.private
            val status = TaskStatus.pending
            val createdDate = binding.tieTaskCreationDateStart.text.toString() ?: c.getCurrentDate()
            val endDate = binding.tieTaskCreationDateEnd.text.toString() ?: c.getCurrentDate()
            tasks.add(Task(idTask, idCliente, title, desc, "A", type, status, createdDate, endDate))
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


}