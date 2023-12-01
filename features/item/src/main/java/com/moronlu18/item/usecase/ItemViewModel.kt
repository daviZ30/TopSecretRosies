package com.moronlu18.item.usecase

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moronlu18.item.entity.item

class ItemViewModel:ViewModel() {

    val newItem = MutableLiveData<item>()

    fun addItem(item: item) {
        newItem.value = item
    }
}