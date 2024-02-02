package com.moronlu18.invoice.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.moronlu18.task.entity.TaskId

@ProvidedTypeConverter
class TaskIdConverter {
    @TypeConverter
    fun TaskIdtoInt(value: Int): TaskId {
        return TaskId(value)
    }
    @TypeConverter
    fun InttoTaskId(taskId: TaskId):Int{
        return taskId.value
    }
}