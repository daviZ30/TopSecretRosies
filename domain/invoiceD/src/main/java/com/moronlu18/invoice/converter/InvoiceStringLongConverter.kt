package com.moronlu18.invoice.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

@ProvidedTypeConverter
class InvoiceStringLongConverter {
    @TypeConverter
    fun toLong(value: String): Long {
        return value.toLong()
    }
    @TypeConverter
    fun fromLong(long : Long):String{
        return long.toString()
    }
}