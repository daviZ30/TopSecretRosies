package com.moronlu18.customer.repository

import android.database.sqlite.SQLiteException
import com.moronlu18.customer.entity.Customer
import com.moronlu18.invoice.InvoiceDatabase
import com.moronlu18.invoice.ui.utils.Resource
import kotlinx.coroutines.flow.Flow

class CusomerRepository {
    fun inset(c:Customer): Resource{
        return try {
            InvoiceDatabase.getInstance().customerDao().insert(c)
            Resource.Success<Customer>(c)
        }catch (e: SQLiteException){
            Resource.Error(e)
        }
    }
    fun getCustomerList(): Flow<List<Customer>> {
        return InvoiceDatabase.getInstance().customerDao().selectAll()
    }
}