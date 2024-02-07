package com.moronlu18.InvoiceDavid.Repository

import android.database.sqlite.SQLiteException
import android.util.Log
import com.moronlu18.invoice.InvoiceDatabase
import com.moronlu18.invoice.entity.Invoice
import kotlinx.coroutines.flow.Flow

class InvoiceRepository {
    fun insert(fa: Invoice): Resource {
        return try {
            Log.e("Log", "ESTOY AQUIII")
            InvoiceDatabase.getInstance().invoiceDao().insert(fa)
            println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + fa.Articulos)
            fa.Articulos.forEach {
                //println("ESTOY AQUI")
                InvoiceDatabase.getInstance().lineaItemDao().insert(it)
            }
            Resource.Success<Invoice>(fa)
        }catch (e: SQLiteException){
            println( e.message)
            Resource.Error(e)
        }
    }

    fun getInvoiceList(): Flow<List<Invoice>> {
        return InvoiceDatabase.getInstance().invoiceDao().selectAll()
    }

}