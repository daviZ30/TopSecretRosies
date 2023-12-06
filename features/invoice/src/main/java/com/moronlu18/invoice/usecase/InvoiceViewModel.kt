package com.moronlu18.invoice.usecase

import android.os.Build
import android.text.Editable
import android.text.TextUtils
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moronlu18.customer.entity.Cliente
import com.moronlu18.customer.repository.ProviderCustomer
import com.moronlu18.invoice.ui.InvoiceState
import com.moronlu18.invoice.ui.firebase.network.Resorces
import kotlinx.coroutines.launch
import java.time.Instant

class InvoiceViewModel : ViewModel() {
    var idFactura = MutableLiveData<String>()
    var idCliente = MutableLiveData<String>()
    var FeEmi = MutableLiveData<String>()
    var FeVen = MutableLiveData<String>()
    var nombre = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var telefono = MutableLiveData<String>()
    val clientes = ProviderCustomer.datasetCustomer
    private var _cliente: Cliente? = null
    val cliente
        get() = _cliente!!

    //var email = MutableLiveData<String>()
    //var password = MutableLiveData<String>()
    private var state = MutableLiveData<InvoiceState>()
    fun validate() {
        when {
            TextUtils.isEmpty(idFactura.value) -> state.value = InvoiceState.idFacturaEmtyError
            TextUtils.isEmpty(idCliente.value) -> state.value = InvoiceState.idClienteEmtyError
            TextUtils.isEmpty(FeEmi.value) -> state.value = InvoiceState.feEmiEmtyError
            TextUtils.isEmpty(FeVen.value) -> state.value = InvoiceState.feVenEmtyError
            !introduceCliente() -> state.value = InvoiceState.idClienteInvalidError
            !ValidateFecha(FeEmi.value) -> state.value = InvoiceState.feEmiInvalidError
            !ValidateFecha(FeVen.value) -> state.value = InvoiceState.feVenInvalidError
            !olderDate(SetDate(FeEmi.value!!)!!,SetDate(FeVen.value!!)!!) ->  state.value = InvoiceState.dateInvalidError
            else -> {
                state.value = InvoiceState.Success
            }

        }
    }
    private fun olderDate(t1: Instant, t2: Instant): Boolean {
        if(t1 != null && t2 != null){
            return t1.isBefore(t2)
        }else{
            return false
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun SetDate(fecha: String): Instant? {
        val dateString = fecha + "T00:00:00Z"
        val instant = Instant.parse(dateString)
        return instant


    }
    private fun ValidateFecha(s: String?): Boolean {
        try {
            if (s != null) {
                val dateString = s + "T00:00:00Z"
                Instant.parse(dateString)
            } else {
                return false
            }
        } catch (e: Exception) {
            return false
        }
        return true
    }

    fun introduceCliente(): Boolean {
        try {
            var i: Int = idCliente.value!!.toInt()
            if (i != null) {
                clientes.forEach {
                    if (it.id == i) {
                        _cliente = it
                        this.nombre.value = it.nombre
                        this.email.value = it.email.value
                        this.telefono.value = it.telefono
                        return true
                    }
                }
            }
        } catch (e: Exception) {
            this.nombre.value = ""
            this.email.value = ""
            this.telefono.value = ""
            return false
        }
        return false
    }

    fun getState(): LiveData<InvoiceState> {
        return state;
    }

}