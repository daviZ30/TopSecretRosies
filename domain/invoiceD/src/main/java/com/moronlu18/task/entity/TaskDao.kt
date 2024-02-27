package com.moronlu18.task.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = ForeignKey.RESTRICT)
    fun insert(task: Task)
    @Delete
    fun delete(task : Task)
    @Query("SELECT * FROM task")
    fun selectAll(): Flow<List<Task>>
    @Query("SELECT * FROM task")
    fun selectAllRAW(): List<Task>
}