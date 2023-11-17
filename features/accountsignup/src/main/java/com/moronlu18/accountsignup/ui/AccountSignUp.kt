package com.moronlu18.accountsignup.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.moronlu18.accountsignup.R
import com.moronlu18.accountsignup.databinding.FragmentAccountSignUpBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountSignUp.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountSignUp : Fragment() {
    private  var _binding : FragmentAccountSignUpBinding? =null ;
    private val binding
        get() = _binding!!


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentAccountSignUpBinding.inflate(inflater,container,false)
        return binding.root
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AccountSignUp.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AccountSignUp().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemListString = arrayListOf("Private","Public","Empty")
        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,itemListString)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spProfile.adapter=adapter;
        binding.spProfile.setSelection(2)
        //Inicializar el listener que se lanza cuando el usuario modifica el valor

        val listener = object : AdapterView.OnItemSelectedListener{
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

        //Se usa el modismo with que dado un objeto se puede modificar propiedades dentro dl bloque
        with(binding.spProfile){
            this.adapter = adapter
            setSelection(2)
            onItemSelectedListener = listener
            onItemSelectedListener=null
        }
    }
}