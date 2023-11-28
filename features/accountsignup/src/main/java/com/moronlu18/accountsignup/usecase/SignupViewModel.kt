package com.moronlu18.accountsignup.usecase

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moronlu18.AuthDirebaseRepository
import com.moronlu18.invoice.ui.firebase.network.Resorces
import com.moronlu18.accountsignup.ui.SignUpState
import kotlinx.coroutines.launch

const val TAG = "ViewModel"

class SignupViewModel : ViewModel() {
    //LiveData que controlan los datos introduccidos en la IU
    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()

    //LiveData que tedrá su Observador en el Fragment y controla las excepciones/casos de  uso de la operacion Login

    private var state = MutableLiveData<SignUpState>()

    //Crear la calase sellada que permitirá las excepciones de la vista

    /**
     * Esta funcion se ejecuta directamente desde el fichero XML al usar databinding
     */
    fun validate() {
        //Log.i(TAG, "El email es: ${email.value} y el password es ${password.value}")
        when {
            TextUtils.isEmpty(email.value) -> state.value = SignUpState.EmailEmptyError
            TextUtils.isEmpty(password.value) -> state.value = SignUpState.PasswordEmptyError
            //EmailFormat
            //PasseordFormat
            else -> {
                //Se crea una corrutina qie suspenda el hilo principal hasta que el
                // bloque withContext del repositotio termina de ejecutarse
                viewModelScope.launch {
                    state.value = SignUpState.Loading(true)
                    //Vamos a ejecutar el login del repositorio -> que pregunta a la capa de la infraestructura
                    val result = AuthDirebaseRepository().login(email.value!!, password.value!!)
                    //is cuando sea un data class
                    state.value = SignUpState.Loading(false)
                    when (result) {
                        is Resorces.Sucess<*> -> {
                            Log.i(TAG, "Login correcto del usuario")
                            state.value = SignUpState.Success(result)
                        }

                        is Resorces.Error -> {
                            Log.i(TAG, "Informacion del dato ${result.exception.message}")
                            state.value = SignUpState.AuthencationError(result.exception.message!!)
                        }
                    }
                }
            }
        }
    }

    /**
     * Se cera solo la funcion de obtencion de la variable state. No se puede modificar su valor fuera de ViewModel
     */
    fun getState(): LiveData<SignUpState> {
        return state;
    }
}

