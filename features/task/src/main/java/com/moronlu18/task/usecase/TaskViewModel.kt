package com.moronlu18.task.usecase

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moronlu18.customer.repository.ProviderCustomer
import com.moronlu18.task.ui.TaskState
import java.text.SimpleDateFormat
import java.util.Locale
import com.moronlu18.task.calendar.CalendarInvoice
import com.moronlu18.task.entity.Task
import com.moronlu18.task.entity.TaskStatus
import com.moronlu18.task.entity.TaskType
import com.moronlu18.task.repository.ProviderTask
import android.widget.Toast

class TaskViewModel : ViewModel() {
    val tasksList: MutableList<Task> = ProviderTask.taskExample
    val customerList = ProviderCustomer.datasetCustomer
    var idTask = MutableLiveData<Int>()
    val title = MutableLiveData<String>()
    val idCustomer = MutableLiveData<Int>() //Recibe la posicion del spinner de customer
    val description = MutableLiveData<String>()
    val type = MutableLiveData<TaskType>()
    val status = MutableLiveData<TaskStatus>()
    val createdDate = MutableLiveData(CalendarInvoice.getCurrentDate())
    val endDate = MutableLiveData<String>()
    val typeselected = MutableLiveData<Int>()
    val statusselected = MutableLiveData<Int>()
    var edit : Boolean = false


    private val state = MutableLiveData<TaskState>()
    fun validate(){
        val customerSelected = idCustomer.value == 0
        when{
            TextUtils.isEmpty(title.value) || title.value?.isBlank()!!  ->state.value = TaskState.TitleIsMandatoryError
            customerSelected -> state.value = TaskState.CustomerUnspecifiedError
            incorrectDateRange(createdDate.value,endDate.value) -> state.value = TaskState.IncorrectDateRangeError
            else ->{ state.value = TaskState.Success }
        }
    }

    /**
     * Función que crea o edita una tarea
     */
     fun makeTask() {
         val customerId = idCustomer.value!!
         val title = this.title.value!!
         val nameCustomer = customerList.find { it.id == customerId }?.getFullName()!!
         val desc = description.value ?: "" //Puede no tener descripión
         val type = getType()
         val status = getStatus()
         val createdDate = this.createdDate.value!!
         val endDate = this.endDate.value ?: "" //Puede tener fecha fin indefinido
        if (idTask.value == null)
            idTask.value = tasksList.lastOrNull()?.idTask?.plus(1) ?: 1 //si no esta vacio devuelve el ultimo id + 1, si esta vacio devuelve 1
        val task =  Task(idTask.value!!, customerId,title, desc, nameCustomer, type, status, createdDate, endDate)
         if (!edit){
            tasksList.add(task)
         }else{
             ProviderTask.updateTask(task)
         }
    }

    /**
     * Función que devuelve si es incorrecto el rango de fechas (createdDate antes que endDate)
     */
    private fun incorrectDateRange(created: String?, end :String? ) : Boolean{
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        if (end.isNullOrBlank()){
            return false //Si no hay fecha fin, el rango no es incorrecto
        }
        val createdDate = dateFormat.parse(created!!) //Siempre tendrá un valor por defecto
        val endDate = dateFormat.parse(end)
        return createdDate!!.after(endDate)
    }

    /**
     * Función que devuelve el tipo de tarea según la posición del spinner
     */
    private fun getType() : TaskType {
        when(typeselected.value){
            //0 -> type.value = TaskType.private
            1 -> type.value = TaskType.call
            2 -> type.value = TaskType.visitor
            else -> type.value = TaskType.private
        }
        return type.value!!
    }

    /**
     * Función que devuelve el estado de tarea según la posición del spinner
     */
    private fun getStatus() : TaskStatus {
        when(statusselected.value){
            //0 -> status.value = TaskStatus.pending
            1 -> status.value = TaskStatus.modified
            2 -> status.value = TaskStatus.overdue
            else -> status.value = TaskStatus.pending
        }
        return status.value!!
    }

    /**
     * Función que devuelve el estado de la creación de la tarea
     */
    fun getState(): LiveData<TaskState> {
        return state;
    }
}