package com.moronlu18.customerun.usecase

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moronlu18.customer.entity.Cliente
import com.moronlu18.customer.repository.ProviderCustomer
import com.moronlu18.customerun.ui.CustomerState
import com.moronlu18.invoice.ui.firebase.Email
import java.util.regex.Pattern

class CustomerViewModel : ViewModel() {
    var email = MutableLiveData<String>()
    var nombre = MutableLiveData<String>()
    var apellidos = MutableLiveData<String>("")
    var telefono = MutableLiveData<String>("")
    var ciudad = MutableLiveData<String>("")
    var direccion = MutableLiveData<String>("")
    var editar: Boolean = false
    var id: Int = 0
    var position:Int =0
    private var state = MutableLiveData<CustomerState>()

    fun validate() {
        when {
            TextUtils.isEmpty(nombre.value) -> state.value = CustomerState.NombreEmtyError
            TextUtils.isEmpty(email.value) -> state.value = CustomerState.EmailEmtyError
            ValidarEmail(email.value) -> state.value = CustomerState.EmailFormatError
            else -> {
                state.value = CustomerState.Success
                if (!editar) {
                    if (ProviderCustomer.datasetCustomer.size > 0) {
                        ProviderCustomer.datasetCustomer.add(
                            Cliente(
                                ProviderCustomer.datasetCustomer.last().id + 1,
                                nombre.value!!,
                                apellidos.value!!,
                                Email(email.value!!),
                                telefono.value!!,
                                ciudad.value!!,
                                direccion.value!!
                            )
                        )
                    } else {
                        ProviderCustomer.datasetCustomer.add(
                            Cliente(
                                1,
                                nombre.value!!,
                                apellidos.value!!,
                                Email(email.value!!),
                                telefono.value!!,
                                ciudad.value!!,
                                direccion.value!!
                            )
                        )

                    }
                } else {
                    ProviderCustomer.datasetCustomer.removeAt(position)
                    ProviderCustomer.datasetCustomer.add(
                        position, Cliente(
                            id,
                            nombre.value!!,
                            apellidos.value!!,
                            Email(email.value!!),
                            telefono.value!!,
                            ciudad.value!!,
                            direccion.value!!
                        )
                    )
                }
            }

        }
    }

    private fun ValidarEmail(value: String?): Boolean {
        val pattern = Pattern.compile("^\\S+@\\S+\\.\\S+")

        if (!pattern.matcher(value).matches()) {
            return true
        }
        return false
    }

    fun getState(): LiveData<CustomerState> {
        return state;
    }
}