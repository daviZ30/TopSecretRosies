package com.moronlu18.task.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.moronlu18.customer.repository.ProviderCustomer
import com.moronlu18.task.calendar.CalendarInvoice
import com.moronlu18.task.entity.Task
import com.moronlu18.task.repository.ProviderTask
import com.moronlu18.taskFragment.databinding.FragmentTaskCreationBinding
import com.moronlu18.task.entity.TaskStatus
import com.moronlu18.task.entity.TaskType
import com.moronlu18.task.usecase.TaskViewModel


class TaskCreationFragment : Fragment() {
    private val tasks: MutableList<Task> = ProviderTask.taskExample
    private val customer = ProviderCustomer.datasetCustomer
    private val c = CalendarInvoice()

    private var _binding: FragmentTaskCreationBinding? = null
    private val binding get() = _binding!!

    private val viewModel : TaskViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var names: MutableList<String> = mutableListOf()
        //Añade los clientes al spinner y si no hay no puedes crear una tarea
        if (customer.isEmpty()) {
            names.add("<No Existen Clientes>")
            binding.btnTaskCreationAdd.isEnabled = false
        } else {
            names.add("--Selecciona un cliente--")
            for (cliente in customer) {
                names.add("${cliente.id}.-" + cliente.getFullName())
            }
        }
        binding.spTaskCreationCliente.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, names)

        viewModel.getState().observe(viewLifecycleOwner){
            when(it){
                TaskState.TitleIsMandatoryError -> {}
                TaskState.CustomerUnspecified -> {}
                TaskState.IncorrectDateRangeError -> {}
                TaskState.Success -> {}
            }
        }


        binding.tieTaskCreationDateStart.setText(c.getCurrentDate()) //Valor por defecto
        //PopUp de Calendario
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
            val idCliente = getIdCliente(binding.spTaskCreationCliente.selectedItem.toString())
            val title = binding.tieTaskCreationTitulo.text.toString() ?: "<Sin Titulo>"
            val nameCustomer = customer.find { it.id == idCliente }?.getFullName()
            val desc = binding.tieTaskCreationDesc.text.toString() ?: ""
            val type = TaskType.private
            val status = TaskStatus.pending
            val createdDate = binding.tieTaskCreationDateStart.text.toString() ?: c.getCurrentDate()
            val endDate = binding.tieTaskCreationDateEnd.text.toString() ?: c.getCurrentDate()
            tasks.add(Task(idTask, idCliente, title, desc, nameCustomer!!, type, status, createdDate, endDate))
            Toast.makeText(requireContext(), "La tarea ha sido creada", Toast.LENGTH_SHORT).show()
            val bundle = Bundle()
            parentFragmentManager.setFragmentResult("key", bundle)
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskCreationBinding.inflate(inflater, container, false)
        binding.viewmodel = this.viewModel
        binding.lifecycleOwner = this
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Devuelve el id del spinner
     */
    fun getIdCliente(customer : String) : Int{
        var id : Int = customer.split(".").first().toInt()
        return id
    }
}