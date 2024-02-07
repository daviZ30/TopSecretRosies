package com.moronlu18.invoice.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.moronlu18.customer.entity.Customer
@ProvidedTypeConverter
class TaskCustomerIdConverter {
    @TypeConverter
    fun customerToInt(value: Customer): Int {
        return value.id.value
    }
    @TypeConverter
    fun intToCustomer(id: Int): Customer?{
        return null
    }
}