package com.moronlu18.customerun.usecase
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moronlu18.customer.repository.ProviderCustomer
import com.moronlu18.customerun.ui.CustomerState
import com.moronlu18.invoice.ui.firebase.network.Resorces
import kotlinx.coroutines.launch

class CustomerViewModel:ViewModel() {
    var email = MutableLiveData<String>()
    var nombre = MutableLiveData<String>()

    private  var state = MutableLiveData<CustomerState>()

    fun  validate () {
        when{
            TextUtils.isEmpty(nombre.value)->state.value = CustomerState.NombreEmtyError
            TextUtils.isEmpty(email.value) -> state.value = CustomerState.EmailEmtyError


            else ->{
                viewModelScope.launch{
                    state.value = CustomerState.Loading(true)

                    val  result = ProviderCustomer.login(email.value!!, nombre.value!!)
                    state.value = CustomerState.Loading(false)
                    when(result){
                        is Resorces.Sucess<*> -> {
                            state.value = CustomerState.Success(result)
                        }

                        is Resorces.Error -> {
                            state.value = CustomerState.AuthencationError(result.exception.message!!)
                        }
                    }
                }
            }

        }
    }
    fun getState(): LiveData<CustomerState> {
        return state;
    }
}