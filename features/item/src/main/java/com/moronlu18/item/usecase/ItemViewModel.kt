package com.moronlu18.item.usecase

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moronlu18.item.entity.item
import com.moronlu18.item.entity.itemType
import com.moronlu18.item.repository.ItemRepository
import com.moronlu18.item.ui.ItemState

class ItemViewModel:ViewModel() {

    val newItem = MutableLiveData<item?>()



    fun clearNewItem() {
        newItem.value = null
    }

    fun addItem(item: item) {
        newItem.value = item

    }



    fun updateItem(updatedItem: item) {
        ItemRepository.updateItem(updatedItem)
        newItem.value = updatedItem
    }


    val itemId = MutableLiveData<String?>()
    val itemType = MutableLiveData<itemType?>()
    val description = MutableLiveData<String?>()


    val name = MutableLiveData<String?>()
    private val _originalRate = MutableLiveData<Double>().apply { value = 0.0 }
    val rateWithTax = MutableLiveData<Double>().apply { value = 0.0 }
    val rate = MutableLiveData<String?>()
    val isTaxable = MutableLiveData<Boolean?>()
    val taxRate = MutableLiveData<Double>().apply { value = 0.02 }


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

        _originalRate.value = itemRate.toDoubleOrNull() ?: 0.0


        if (isTaxable.value == true) {
            applyTaxToRate()
        } else {
            _originalRate.value = itemRate.toDoubleOrNull() ?: 0.0
            rateWithTax.value = itemRate.toDoubleOrNull() ?: 0.0
        }

        _itemState.value = null
    }

    private val _itemState = MutableLiveData<ItemState?>()
    val itemState: MutableLiveData<ItemState?> get() = _itemState



    fun applyTaxToRate() {
        val originalRate = _originalRate.value ?: 0.0
        val taxRatePercentage = taxRate.value ?: 0.0
        val taxAmount = originalRate * taxRatePercentage
        rateWithTax.value = originalRate + taxAmount
        _itemState.value = ItemState.TaxableItem
    }

    fun updateItemDetails(itemId: Int, itemName: String?, itemRate: Double, itemType: itemType, itemDescription: String?, isTaxable: Boolean) {
        this.itemId.value = itemId.toString()
        this.name.value = itemName
        this.rate.value = String.format("%.2f", itemRate)
        this.itemType.value = itemType
        this.description.value = itemDescription
        this.isTaxable.value = isTaxable
    }

    fun clearItemDetails() {
        itemId.value = null
        name.value = null
        rate.value = null
        itemType.value = null
        description.value = null
        isTaxable.value = null
    }

    fun sortItemListByDescription() {
        val itemList = ItemRepository.getItemList()
        val sortedList = itemList.sortedBy { it.description }
        newItem.value = null
        newItem.value = sortedList.firstOrNull()
    }


}