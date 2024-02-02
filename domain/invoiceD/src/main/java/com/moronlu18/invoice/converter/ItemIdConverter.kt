package com.moronlu18.invoice.converter

import androidx.room.TypeConverter
import com.moronlu18.item.entity.ItemId

class ItemIdTypeConverter {

    @TypeConverter
    fun fromItemId(value: ItemId): Int {
        return value.value
    }

    @TypeConverter
    fun toItemID(value: Int): ItemId {
        return ItemId(value)
    }
}
