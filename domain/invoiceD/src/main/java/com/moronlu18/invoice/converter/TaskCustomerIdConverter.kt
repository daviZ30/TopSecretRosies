package com.moronlu18.invoice.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.moronlu18.customer.entity.Customer
import com.moronlu18.customer.repository.ProviderCustomer

@ProvidedTypeConverter
class TaskCustomerIdConverter {
    @TypeConverter
    fun toCustomer(id: Int): Customer?{
        return ProviderCustomer.datasetCustomer.find { it.id.value == id } //Corregir
    }
    @TypeConverter
    fun fromCustomer(value: Customer): Int {
        return value.id.value
    }
}