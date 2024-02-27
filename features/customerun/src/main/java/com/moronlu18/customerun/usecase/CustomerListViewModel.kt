package com.moronlu18.customerun.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.signup.utils.Locator
import com.moronlu18.customer.repository.CustomerRepository
import com.moronlu18.customerun.ui.CustomerListState
import kotlinx.coroutines.launch

class CustomerListViewModel : ViewModel() {
   // private val customerRepository = InvoiceRepository()
    var allcustomers = CustomerRepository.getCustomerList().asLiveData()

    private var state = MutableLiveData<CustomerListState>()
    fun getState(): LiveData<CustomerListState> {
        return state
    }

    fun validate() {
        viewModelScope.launch {
        when  {
             allcustomers.value?.isEmpty() == true -> CustomerListState.noDataError
            else -> {
                if (Locator.invoicePreferencesRepository.getCustomerOr() == "Id") {
                    sortId()
                } else if (Locator.invoicePreferencesRepository.getCustomerOr() == "Nombre") {
                    sortName()
                }
                state.value = CustomerListState.Success
            }
        }
            }
    }

    fun sortName() {
        allcustomers.value?.sortedBy{ it.nombre }
    }

    fun sortId() {
        allcustomers.value?.sortedBy{ it.id.value }
    }
}