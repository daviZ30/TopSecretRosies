package com.moronlu18.task.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.moronlu18.customer.repository.ProviderCustomer
import com.moronlu18.task.entity.Task
import com.moronlu18.task.repository.ProviderTask
import com.moronlu18.taskFragment.databinding.FragmentTaskCreationBinding
import com.google.android.material.textfield.TextInputEditText
import com.moronlu18.taskFragment.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class TaskCreationFragment : Fragment() {
    var clientes = ProviderCustomer.datasetCustomer

    private var _binding: FragmentTaskCreationBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var names : MutableList<String> = mutableListOf()
        for (cliente in clientes) {
            names.add("${cliente.id}.-"+ cliente.getFullName())
        }
        binding.spTaskCreationCliente.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, names)
        binding.tieTaskCreationDateStart.setText(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date()))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTaskCreationBinding.inflate(inflater, container, false)
        return binding.root
    }

}