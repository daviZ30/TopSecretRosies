package com.moronlu18.invoice.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.signup.utils.Locator
import com.moronlu18.InvoiceDavid.Repository.InvoiceRepository
import com.moronlu18.InvoiceDavid.entity.InvoiceId
import com.moronlu18.InvoiceDavid.entity.LineaItem
import com.moronlu18.customer.repository.ProviderCustomer
import com.moronlu18.invoice.Repository.ProviderInvoice
import com.moronlu18.invoice.entity.Invoice
import com.moronlu18.invoice.ui.InvoiceListState
import kotlinx.coroutines.launch

class InvoiceListViewModel : ViewModel() {
    var allinvoice = InvoiceRepository.getInvoiceList().asLiveData()

    private var state = MutableLiveData<InvoiceListState>()

    fun getState(): LiveData<InvoiceListState> {
        return state;
    }
    fun getLineaItem(id:Int):List<LineaItem>{

       return InvoiceRepository.getLineaItemList(id)
    }
    fun removeInvoice(invoice: Invoice){
        InvoiceRepository.deleteLineaItems(InvoiceRepository.getLineaItemList(invoice.id.value))
        //println("Articulos: ${InvoiceRepository.getLineaItemList(invoice.id.value)}")
        InvoiceRepository.deleteInvoice(invoice)
    }
    fun getInvoiceList() {

        viewModelScope.launch {
            when {
                allinvoice.value?.isEmpty() == true -> state.value = InvoiceListState.noDataError
                else -> {
                    state.value = InvoiceListState.Success

                    if (Locator.invoicePreferencesRepository.getInvoiceOr() == "Id") {
                        //sortId()
                    } else if (Locator.invoicePreferencesRepository.getInvoiceOr() == "No") {
                       // sortNombre()
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