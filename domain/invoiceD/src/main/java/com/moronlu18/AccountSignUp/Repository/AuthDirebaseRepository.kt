package com.moronlu18.AccountSignUp.Repository

import com.google.firebase.auth.FirebaseAuth
import com.moronlu18.accountsignup.network.Resorces
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.Exception

class AuthDirebaseRepository() {
    private var authFirebase = FirebaseAuth.getInstance()
    suspend fun login (email:String, password: String): Resorces {
        //Este  códido se ejecuta en un hilo especifico para operaciones entrada/salida (IO)
        withContext(Dispatchers.IO){
            delay(3000)
            //Se ejecutara el codigo de consulta a FireBase. Que puede tardar mas de 5s y en ese
            //caso se obtiene el error ANR (Android Not Responding) porque puede bloquear la vista
        }
        return Resorces.Error(Exception("El password es incorrecto"))
    }
}