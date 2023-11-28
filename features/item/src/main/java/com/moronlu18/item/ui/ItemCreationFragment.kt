package com.moronlu18.item.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.moronlu18.itemcreation.R


class ItemCreationFragment : Fragment() {

    private lateinit var spinnerItemType: Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_creation, container, false)

        spinnerItemType = view.findViewById(R.id.spnType)

        val itemTypes = arrayOf("PRODUCT", "SERVICE")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itemTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerItemType.adapter = adapter


        spinnerItemType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedType = parent?.getItemAtPosition(position).toString()
                showToast("Tipo seleccionado: $selectedType")

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        return view
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


}