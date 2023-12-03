package com.moronlu18.item.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moronlu18.item.entity.item
import com.moronlu18.item.ui.ItemState

class ItemViewModel:ViewModel() {

    val newItem = MutableLiveData<item?>()

    fun clearNewItem() {
        newItem.value = null
    }

    fun addItem(item: item) {
        newItem.value = item

    }

    val name = MutableLiveData<String>()
    val rate = MutableLiveData<String>()
    val isTaxable = MutableLiveData<Boolean>()
    val taxRate = MutableLiveData<Double>().apply { value = 10.0 }



    fun validateFields(itemName: String, itemRate: String) {

        if (itemName.isEmpty()) {
            _itemState.value = ItemState.NameEmptyError("Nombre no puede ser nulo")
            return
        }


        if (itemRate.toDoubleOrNull() == null) {
            _itemState.value = ItemState.RateFormatError("Precio debe ser un número")
            return
        }


        if (itemName.isEmpty() || itemRate.isEmpty()) {
            _itemState.value = ItemState.RequiredDataMissing
            return
        }

        if (isTaxable.value == true) {
            applyTaxToRate()
        }


        _itemState.value = null
    }

    private val _itemState = MutableLiveData<ItemState?>()
    val itemState: MutableLiveData<ItemState?> get() = _itemState



    fun applyTaxToRate() {
        val originalRate = rate.value?.toDoubleOrNull() ?: 0.0
        val taxRatePercentage = taxRate.value ?: 0.0
        val taxAmount = originalRate * (taxRatePercentage / 100.0)
        val rateWithTax = originalRate + taxAmount

        rate.value = rateWithTax.toString()
        _itemState.value = ItemState.TaxableItem
    }






}