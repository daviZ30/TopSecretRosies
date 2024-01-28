package com.moronlu18.InvoiceDavid.Repository

import com.moronlu18.invoice.InvoiceDatabase
import com.moronlu18.invoice.entity.Invoice
import kotlinx.coroutines.flow.Flow

class InvoiceRepository {
    fun insert(user: Invoice) {
        InvoiceDatabase.getInstance()?.invoiceDao()?.insert(user)
    }

    fun getInvoiceList(): Flow<List<Invoice>> {
        return InvoiceDatabase.getInstance()?.invoiceDao()!!.selectAll()
    }

}