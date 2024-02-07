package com.moronlu18.invoice.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.signup.utils.Locator
import com.moronlu18.InvoiceDavid.Repository.InvoiceRepository
import com.moronlu18.customer.repository.ProviderCustomer
import com.moronlu18.invoice.Repository.ProviderInvoice
import com.moronlu18.invoice.entity.Invoice
import com.moronlu18.invoice.ui.InvoiceListState
import kotlinx.coroutines.launch

class InvoiceListViewModel : ViewModel() {
    val _facturas = ProviderInvoice.datasetFactura
    private val invoiceRepository = InvoiceRepository()
    var allinvoice = invoiceRepository.getInvoiceList().asLiveData()


    val facturas
        get() = _facturas!!
    private var state = MutableLiveData<InvoiceListState>()

    fun sortNombre() {
        facturas.sortBy { ProviderCustomer.GetCliente(it.idCliente)?.nombre }
    }

    fun sortId() {
        facturas.sortBy { ProviderCustomer.GetCliente(it.idCliente)?.id?.value }
    }

    fun getState(): LiveData<InvoiceListState> {
        return state;
    }
    fun getInvoiceList() {

        viewModelScope.launch {
            when {
                allinvoice.value?.isEmpty() == true -> state.value = InvoiceListState.noDataError
                else -> {
                    state.value = InvoiceListState.Success

                    if (Locator.userPreferencesRepository.getInvoiceOr() == "Id") {
                        sortId()
                    } else if (Locator.userPreferencesRepository.getInvoiceOr() == "No") {
                        sortNombre()
                    }

                }
            }
        }
    }
   /* fun validate() {
        when {
            facturas.size == 0 -> state.value = InvoiceListState.noDataError
            else -> {
                if (Locator.userPreferencesRepository.getInvoiceOr() == "Id") {
                    sortId()
                } else if (Locator.userPreferencesRepository.getInvoiceOr() == "No") {
                    sortNombre()
                }
                /*viewModelScope.launch(Dispatchers.IO) {
                    invoiceRepository.insert(
                        Invoice(
                            InvoiceId(2),
                            2,
                            SetFecha("2021-01-20"),
                            SetFecha("2021-01-20"),
                            Articulos = mutableListOf<LineaItem>(
                                LineaItem(
                                    1,
                                    1,
                                    1,
                                    1.5,
                                    45.5
                                )
                            ),
                            InvoiceStatus.Pending
                        )
                    )
                }*/
                state.value = InvoiceListState.Success

                viewModelScope.launch(Dispatchers.IO) {
                    println(".-+----------------" + allinvoice.value as List<Invoice>)
                }
            }
        }
    }*/


}