package com.moronlu18.invoice.usecase

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.signup.utils.Locator
import com.moronlu18.customer.repository.ProviderCustomer
import com.moronlu18.invoice.Repository.ProviderInvoice
import com.moronlu18.invoice.ui.InvoiceListState
import com.moronlu18.invoice.ui.InvoiceState

class InvoiceListViewModel : ViewModel() {
    val _facturas = ProviderInvoice.datasetFactura
    val facturas
        get() = _facturas!!
    private var state = MutableLiveData<InvoiceListState>()

    fun sortNombre() {
        facturas.sortBy { ProviderCustomer.GetCliente(it.idCliente)?.nombre }
    }

    fun sortId() {
        facturas.sortBy { ProviderCustomer.GetCliente(it.idCliente)?.id }
    }

    fun getState(): LiveData<InvoiceListState> {
        return state;
    }


    fun validate() {
        when {
            facturas.size == 0 -> state.value = InvoiceListState.noDataError
            else -> {
                if (Locator.userPreferencesRepository.getInvoiceOr() == "Id") {
                    println("--------------------aaaaaaaaaaaaaaa" + Locator.userPreferencesRepository.getInvoiceOr())
                    sortId()
                } else if (Locator.userPreferencesRepository.getInvoiceOr() == "No") {
                    println("--------------------aaaaaaaaaaaaaaa" + Locator.userPreferencesRepository.getInvoiceOr())
                    sortNombre()
                }
                println("--------------------aaaaaaaaaaaaaaa" + Locator.userPreferencesRepository.getInvoiceOr())

                state.value = InvoiceListState.Success
            }

        }
    }

}