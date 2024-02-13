package com.moronlu18.InvoiceDavid.entity

import androidx.room.Dao
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.moronlu18.invoice.entity.Invoice
import kotlinx.coroutines.flow.Flow
@Dao
interface LineaItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert (lineaItem: LineaItem) : Long

    @Update
    fun update (lineaItem: LineaItem)

    @Query("SELECT * FROM LineaItem")
    fun selectAll(): Flow<List<LineaItem>>
}