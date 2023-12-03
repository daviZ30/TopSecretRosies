package com.moronlu18.invoice.usecase

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moronlu18.customer.repository.ProviderCustomer
import com.moronlu18.invoice.ui.InvoiceState
import com.moronlu18.invoice.ui.firebase.network.Resorces
import kotlinx.coroutines.launch

class InvoiceViewModel:ViewModel() {
    var idFactura = MutableLiveData<String>()
    var idCliente = MutableLiveData<String>()
    //var email = MutableLiveData<String>()
    //var password = MutableLiveData<String>()
    private  var state = MutableLiveData<InvoiceState>()
    fun  validate () {
        when{
            TextUtils.isEmpty(idFactura.value)->state.value = InvoiceState.idFacturaEmtyError
            TextUtils.isEmpty(idCliente.value)->state.value = InvoiceState.idClienteEmtyError

            else ->{
                state.value = InvoiceState.Success
            }

        }
    }
    fun getState(): LiveData<InvoiceState> {
        return state;
    }

}