package com.moronlu18.item.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.moronlu18.item.entity.item
import com.moronlu18.item.entity.itemType
import com.moronlu18.item.usecase.ItemViewModel
import com.moronlu18.itemcreation.R


class ItemCreationFragment : Fragment() {

    private lateinit var tilEditId: TextInputEditText
    private lateinit var tilEditName: TextInputEditText
    private lateinit var tilEditRate: TextInputEditText
    private lateinit var spinnerItemType: Spinner
    private lateinit var tilEditDescription: TextInputEditText
    private lateinit var chbIsTaxable: CheckBox
    private lateinit var fabAdd: FloatingActionButton

    private lateinit var itemViewModel: ItemViewModel


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

        tilEditId = view.findViewById(R.id.tilEditId)
        tilEditName = view.findViewById(R.id.tilEditName)
        tilEditRate = view.findViewById(R.id.tilEditRate)
        spinnerItemType = view.findViewById(R.id.spnType)
        tilEditDescription = view.findViewById(R.id.tilEditDescription)
        chbIsTaxable = view.findViewById(R.id.chbIsTaxable)
        fabAdd = view.findViewById(R.id.fabadd)

        val itemTypes = arrayOf("PRODUCT", "SERVICE")

        itemViewModel = ViewModelProvider(requireActivity()).get(ItemViewModel::class.java)

        fabAdd.setOnClickListener {
            val newItem = createItemFromInput()
            itemViewModel.addItem(newItem)
            findNavController().navigateUp()
        }

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

    private fun createItemFromInput(): item {
        val id = tilEditId.text.toString().toIntOrNull() ?: 0
        val name = tilEditName.text.toString()
        val rate = tilEditRate.text.toString().toDoubleOrNull() ?: 0.0
        val typeString = spinnerItemType.selectedItem.toString()
        val type = itemType.valueOf(typeString)
        val description = tilEditDescription.text.toString()
        val isTaxable = chbIsTaxable.isChecked

        return item(id, name, rate, type, description, isTaxable)
    }


}