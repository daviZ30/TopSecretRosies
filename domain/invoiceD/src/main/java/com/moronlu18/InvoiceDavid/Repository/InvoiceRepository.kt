package com.moronlu18.InvoiceDavid.Repository

import android.database.sqlite.SQLiteException
import com.moronlu18.InvoiceDavid.entity.LineaItem
import com.moronlu18.invoice.InvoiceDatabase
import com.moronlu18.invoice.entity.Invoice
import kotlinx.coroutines.flow.Flow

class InvoiceRepository {
    companion object
    {
        fun insertInvoice(fa: Invoice): Resource {
            return try {
                InvoiceDatabase.getInstance().invoiceDao().insert(fa)
                Resource.Success<Invoice>(fa)
            }catch (e: SQLiteException){
                println( e.message)
                Resource.Error(e)
            }
        }
        fun updateInvoice(fa: Invoice): Resource {
            return try {
                InvoiceDatabase.getInstance().invoiceDao().update(fa)
                Resource.Success<Invoice>(fa)
            }catch (e: SQLiteException){
                println( e.message)
                Resource.Error(e)
            }
        }

        fun insertLineaItems(lista: MutableList<LineaItem>): Resource {
            return try {
                lista.forEach {
                    InvoiceDatabase.getInstance().lineaItemDao().insert(it)
                }

                Resource.Success<MutableList<LineaItem>>(lista)
            }catch (e: SQLiteException){
                println( e.message)
                Resource.Error(e)
            }
        }


        fun getInvoiceList(): Flow<List<Invoice>> {
            return InvoiceDatabase.getInstance().invoiceDao().selectAll()
        }
        fun getInvoiceListRAW(): List<Invoice> {
            return InvoiceDatabase.getInstance().invoiceDao().selectAllRAW()
        }
        fun getLineaItemList(id:Int): List<LineaItem> {
            return InvoiceDatabase.getInstance().lineaItemDao().selectFromInvoice(id)
        }
    }


}