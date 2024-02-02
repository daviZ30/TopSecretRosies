package com.moronlu18.invoice.usecase

import androidx.lifecycle.ViewModel
import com.moronlu18.customer.entity.Customer
import com.moronlu18.customer.repository.ProviderCustomer
import com.moronlu18.invoice.Repository.ProviderInvoice

class InvoiceDetailsViewModel:ViewModel() {
    val _facturas = ProviderInvoice.datasetFactura
    val facturas
        get() = _facturas!!

    fun GetCliente(id:Int): Customer? {
        return ProviderCustomer.GetCliente(id)
    }
}