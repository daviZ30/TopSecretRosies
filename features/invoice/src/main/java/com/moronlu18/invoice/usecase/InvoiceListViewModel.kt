package com.moronlu18.invoice.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.signup.utils.Locator
import com.moronlu18.InvoiceDavid.Repository.InvoiceRepository
import com.moronlu18.InvoiceDavid.entity.InvoiceId
import com.moronlu18.InvoiceDavid.entity.InvoiceStatus
import com.moronlu18.customer.repository.ProviderCustomer
import com.moronlu18.invoice.Repository.ProviderInvoice
import com.moronlu18.invoice.entity.Invoice
import com.moronlu18.invoice.ui.InvoiceListState
import com.moronlu18.invoice.ui.InvoiceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant

class InvoiceListViewModel : ViewModel() {
    val _facturas = ProviderInvoice.datasetFactura
    val invoiceRepository: InvoiceRepository = InvoiceRepository()
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

    fun validate() {
        when {
            facturas.size == 0 -> state.value = InvoiceListState.noDataError
            else -> {
                if (Locator.userPreferencesRepository.getInvoiceOr() == "Id") {
                    sortId()
                } else if (Locator.userPreferencesRepository.getInvoiceOr() == "No") {
                    sortNombre()
                }
                viewModelScope.launch(Dispatchers.IO) {
                    invoiceRepository.insert(
                        Invoice(
                            InvoiceId(1),
                            1,
                            SetFecha("2021-01-20"),
                            SetFecha("2021-01-20"),
                            InvoiceStatus.Pending
                        )
                    )
                }
                state.value = InvoiceListState.Success
            }

        }
    }

    private fun SetFecha(fecha: String): Instant {
        val dateString = fecha + "T00:00:00Z"
        //val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        //val localDateTime = LocalDateTime.parse(dateString, formatter)
        val instant = Instant.parse(dateString)
        //return localDateTime.toInstant(ZoneOffset.MAX)
        return instant
    }

}