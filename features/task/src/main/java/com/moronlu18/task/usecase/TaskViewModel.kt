package com.moronlu18.task.usecase

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moronlu18.task.ui.TaskState

class TaskViewModel : ViewModel() {
    val title = MutableLiveData<String>()
    val customer = MutableLiveData<String>()
    val createdDate = MutableLiveData<String>()
    val endDate = MutableLiveData<String>()
    private val state = MutableLiveData<TaskState>()
    fun validate(){
        when{
            TextUtils.isEmpty(title.value)->state.value = TaskState.TitleIsMandatoryError
            else ->{
                state.value = TaskState.Success
            }
        }
    }
    fun getState(): LiveData<TaskState> {
        return state;
    }
}