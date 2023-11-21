package com.moronlu18.accountsignup.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.moronlu18.accountsignup.R
import com.moronlu18.accountsignup.databinding.FragmentAccountSignUpBinding


class AccountSignUp : Fragment() {
    private var _binding: FragmentAccountSignUpBinding? = null;
    private val binding
        get() = _binding!!

    //Se iniciará posteriormente
    //private lateinit var viewModel: SignupViewModel

    private val viewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAccountSignUpBinding.inflate(inflater, container, false)
        //pasamos a la interfaz la instancia del ViewModel para que actualize
        // o recoga los valores del email y password automaticamente y se asocia el evento onClick del boton a una funcion
        binding.viewmodel = this.viewModel
        //IMPORTANTE: hay que establecer el fragment/avtivity vinculado a binding para actualizar los valores de
        // Binging en base al ciclo de vida
        binding.lifecycleOwner = this

        return binding.root
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AccountSignUp().apply {

            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemListString = arrayListOf("Private", "Public", "Empty")
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itemListString)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spProfile.adapter = adapter;
        binding.spProfile.setSelection(2)
        //Inicializar el listener que se lanza cuando el usuario modifica el valor

        val listener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val profile = parent?.adapter?.getItem(position)
                Toast.makeText(requireContext(), "Elemento pulsado $profile", Toast.LENGTH_SHORT)
                //El ArrayList del adapter se encuentra en esta funcion mediente itemList


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        viewModel.getState().observe(viewLifecycleOwner, Observer {
            when(it){
                SignUpState.EmailEmptyError -> setEmailEmptyError()
                SignUpState.PasswordEmptyError -> setPasswordEmptyError()
                else -> onSuccess()
            }
        })

        //Se usa el modismo with que dado un objeto se puede modificar propiedades dentro dl bloque
        with(binding.spProfile) {
            this.adapter = adapter
            setSelection(2)
            onItemSelectedListener = listener
            onItemSelectedListener = null
        }
    }

    private fun onSuccess() {
        Toast.makeText(requireContext(),"Caso de éxito en el Login",Toast.LENGTH_SHORT).show()
    }

    /**
     * Funcionq que muestra el error de Password Empty
     */
    private fun setPasswordEmptyError() {
        binding.Email.error = getString(R.string.errPasswordEmptyError)
        binding.Email.requestFocus() //El cursor del foco se coloca sobre el error
    }

    /**
     * Funcionq que muestra el error de Email Empty
     */
    private fun setEmailEmptyError() {
        binding.Email.error = getString(R.string.errEmailEmpty)
        binding.Email.requestFocus() //El cursor del foco se coloca sobre el error
    }
}