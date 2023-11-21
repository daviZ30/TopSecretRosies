package com.moronlu18.accountsignup.ui

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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
            else -> state.value = SignUpState.Success
        }
    }

    /**
     * Se cera solo la funcion de obtencion de la variable state. No se puede modificar su valor fuera de ViewModel
     */
    fun getState(): LiveData<SignUpState> {
        return state;
    }
}

