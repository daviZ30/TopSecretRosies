package com.moronlu18.task.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
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

    inner class textWatcher(var til: TextInputLayout) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        }
        override fun afterTextChanged(s: Editable) {
            til.isErrorEnabled = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inicializeSpinners()

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
        //Para comprobar que el valor del spinner de clientes no sea el texto de ayuda
        viewModel.customer.value = binding.spTaskCreationCliente.selectedItem.toString()

        viewModel.getState().observe(viewLifecycleOwner){
            when(it){
                TaskState.TitleIsMandatoryError -> {
                    binding.tilTaskCreationTitulo.error = "Titulo obligatorio"
                }
                TaskState.CustomerUnspecified -> {
                    Toast.makeText(requireContext(), "Selecciona un cliente", Toast.LENGTH_SHORT).show()
                }
                TaskState.IncorrectDateRangeError -> {
                    binding.tilTaskCreationDateEnd.error =
                        "La fecha fin no puede ser menor que la fecha inicio"}
                TaskState.Success -> {createTask()}
            }
        }

        binding.btnTaskCreationAdd.setOnClickListener {
                viewModel.validate()
            }

        binding.tieTaskCreationTitulo.addTextChangedListener {
            textWatcher(binding.tilTaskCreationTitulo)
        }
        }

    private fun inicializeSpinners() {
        val names: MutableList<String> = mutableListOf()
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

        binding.spTaskCreationType.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, TaskType.values())

        binding.spTaskCreationStatus.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, TaskStatus.values())
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
    private fun getIdCustomer(customer : String) : Int{
        try {
            var id : Int = customer.split(".").first().toInt()
            return id
        }catch (e: NumberFormatException){
            return -1
        }
    }

    private fun createTask() {
        val customerId = getIdCustomer(binding.spTaskCreationCliente.selectedItem.toString())
        if (customerId != -1){
            val idTask = tasks.last().idTask + 1
            val title = binding.tieTaskCreationTitulo.text.toString()
            val nameCustomer = customer.find { it.id == customerId }?.getFullName()
            val desc = binding.tieTaskCreationDesc.text.toString()
            val type = TaskType.valueOf(binding.spTaskCreationType.selectedItem.toString())
            val status = TaskStatus.valueOf(binding.spTaskCreationStatus.selectedItem.toString())
            val createdDate = binding.tieTaskCreationDateStart.text.toString()
            val endDate = binding.tieTaskCreationDateEnd.text.toString()
            tasks.add(Task(idTask, customerId, title, desc, nameCustomer!!, type, status, createdDate, endDate))
            Toast.makeText(requireContext(), "La tarea ha sido creada", Toast.LENGTH_SHORT).show()
            val bundle = Bundle()
            parentFragmentManager.setFragmentResult("key", bundle)
            findNavController().popBackStack()
        }
        else {
        }
    }
}