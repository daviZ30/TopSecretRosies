package com.moronlu18.invoice.converter

import androidx.room.TypeConverter
import com.moronlu18.invoice.ui.firebase.Email

class CustomerEmailTypeConverter {
    @TypeConverter
    fun toInvoiceId(value: String): Email {
        return Email(value)
    }
    @TypeConverter
    fun fromInvoiceId(email: Email):String{
        return email.value
    }
}