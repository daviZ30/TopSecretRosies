package com.moronlu18.task.entity

import androidx.room.Dao
import androidx.room.ForeignKey
import androidx.room.Insert

@Dao
interface TaskDao {
    @Insert(onConflict = ForeignKey.RESTRICT)
    fun insert(task: Task)
}