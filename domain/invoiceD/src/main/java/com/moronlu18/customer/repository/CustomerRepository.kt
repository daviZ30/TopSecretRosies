package com.moronlu18.customer.repository

import android.database.sqlite.SQLiteException
import com.moronlu18.customer.entity.Customer
import com.moronlu18.invoice.InvoiceDatabase
import com.moronlu18.invoice.ui.utils.Resource
import kotlinx.coroutines.flow.Flow

class CustomerRepository {
    companion object {
        fun insert(c: Customer): Resource {
            return try {
                InvoiceDatabase.getInstance().customerDao().insert(c)
                Resource.Success<Customer>(c)
            } catch (e: SQLiteException) {
                Resource.Error(e)
            }
        }
        fun getCustomerList(): Flow<List<Customer>> {
            return InvoiceDatabase.getInstance().customerDao().selectAll()
        }
        fun getCustomerListRAW(): List<Customer> {
            return InvoiceDatabase.getInstance().customerDao().selectAllRAW()
        }
        fun getCliente(id:Int):Customer{
            return InvoiceDatabase.getInstance().customerDao().selectCustomer(id)
        }
    }


}