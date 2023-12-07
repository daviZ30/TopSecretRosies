package com.moronlu18.task.usecase

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moronlu18.task.ui.TaskState
import java.text.SimpleDateFormat
import java.util.Locale

class TaskViewModel : ViewModel() {
    val title = MutableLiveData<String>()
    val customer = MutableLiveData<String>()
    val createdDate = MutableLiveData<String>()
    val endDate = MutableLiveData<String>()
    private val state = MutableLiveData<TaskState>()
    fun validate(){
        when{
            TextUtils.isEmpty(title.value)->state.value = TaskState.TitleIsMandatoryError
            incorrectDateRange(createdDate.value,endDate.value) -> state.value = TaskState.IncorrectDateRangeError
            else ->{
                state.value = TaskState.Success
            }
        }
    }

    private fun incorrectDateRange(created: String?, end :String? ) : Boolean{
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        if (end == null){
            return true
        }
        val createdDate = dateFormat.parse(created!!) //Siempre tendrá un valor por defecto
        val endDate = dateFormat.parse(end)
        return !createdDate!!.after(endDate)
    }

    fun getState(): LiveData<TaskState> {
        return state;
    }
}