package com.moronlu18.customerun.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputLayout
import com.moronlu18.customerun.databinding.FragmentCustomerCreationBinding
import com.moronlu18.customerun.usecase.CustomerViewModel


class CustomerCreationFragment : Fragment() {
    private var _binding: FragmentCustomerCreationBinding? = null

    private val binding
        get() = _binding!!

    private val viewModel: CustomerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    inner class textWatcher(var t: TextInputLayout) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable) {
            t.isErrorEnabled = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerCreationBinding.inflate(inflater, container, false)
        binding.viewmodel = this.viewModel

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tieNombreCustomerCreation.addTextChangedListener(textWatcher(binding.tilNombreCustomerCreation))
        binding.tieApellidosCustomerCreation.addTextChangedListener(textWatcher(binding.tilApellidosCustomerCreation))
        binding.tieCorreoCustomerCreation.addTextChangedListener(textWatcher(binding.tilCorreoCustomerCreation))

        viewModel.validate()

        viewModel.getState().observe(viewLifecycleOwner) {
            when (it) {
                CustomerState.NombreEmtyError -> setNombreEmptyError()
                CustomerState.ApellidosEmtyError -> SetApellidoEmptyError()
                CustomerState.EmailEmtyError -> SetEmailEmptyError()
                is CustomerState.AuthencationError -> mostrarMensaje(it.message)

                else -> onSuccess()
            }
        }
    }

    private fun SetApellidoEmptyError() {
        binding.tilApellidosCustomerCreation.error ="Vacio"
        binding.tieApellidosCustomerCreation.requestFocus()
    }

    private fun SetEmailEmptyError() {
        binding.tilCorreoCustomerCreation.error = "Email nunca puede estar vacio"
        binding.tieCorreoCustomerCreation.requestFocus()

    }

    private fun setNombreEmptyError() {
        binding.tilNombreCustomerCreation.error = "Nombre"
        binding.tieNombreCustomerCreation.requestFocus()

    }

    private fun mostrarMensaje(message: String) {
        TODO("Not yet implemented")
    }

    private fun onSuccess() {

    }
}