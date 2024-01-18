package com.moronlu18.invoice.usecase

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moronlu18.invoice.Repository.ProviderInvoice
import com.moronlu18.invoice.ui.InvoiceListState
import com.moronlu18.invoice.ui.InvoiceState

class InvoiceListViewModel: ViewModel() {
    val _facturas = ProviderInvoice.datasetFactura
    val facturas
        get() = _facturas!!
    private var state = MutableLiveData<InvoiceListState>()

    fun sortNombre(){
        facturas.sortBy { it.Cliente.nombre }
    }
    fun sortId(){
        facturas.sortBy { it.Cliente.id }
    }
    fun getState(): LiveData<InvoiceListState> {
        return state;
    }


    fun validate() {
        when {
            facturas.size == 0 -> state.value = InvoiceListState.noDataError
            else -> {
                println("--------------------" + facturas.get(0).CantidadArticulos())
                sortId()
                state.value = InvoiceListState.Success
            }

        }
    }

}