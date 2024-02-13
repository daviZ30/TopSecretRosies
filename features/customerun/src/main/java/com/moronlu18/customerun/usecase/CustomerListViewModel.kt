package com.moronlu18.customerun.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.signup.utils.Locator
import com.moronlu18.InvoiceDavid.Repository.InvoiceRepository
import com.moronlu18.customer.repository.ProviderCustomer
import com.moronlu18.customerun.ui.CustomerListState

class CustomerListViewModel : ViewModel() {
    private val _clientes = ProviderCustomer.datasetCustomer
   // private val customerRepository = InvoiceRepository()
    var allinvoice = InvoiceRepository.getInvoiceList().asLiveData()

    val clientes
        get() = _clientes
    private var state = MutableLiveData<CustomerListState>()
    fun getState(): LiveData<CustomerListState> {
        return state
    }

    fun validate() {
        when (clientes.size) {
            0 -> CustomerListState.noDataError
            else -> {
                if (Locator.userPreferencesRepository.getCustomerOr() == "Id") {
                    sortId()
                } else if (Locator.userPreferencesRepository.getCustomerOr() == "Nombre") {
                    sortName()
                }
                state.value = CustomerListState.Success
            }
        }
    }

    fun sortName() {
        ProviderCustomer.datasetCustomer.sortBy { it.nombre }
    }

    fun sortId() {
        ProviderCustomer.datasetCustomer.sortBy { it.id.value }
    }
}