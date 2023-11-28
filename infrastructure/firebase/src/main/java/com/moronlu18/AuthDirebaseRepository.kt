package com.moronlu18


import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

import com.moronlu18.invoice.ui.firebase.Account
import com.moronlu18.invoice.ui.firebase.AccountState
import com.moronlu18.invoice.ui.firebase.Email
import com.moronlu18.invoice.ui.firebase.network.Resorces
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception


class AuthDirebaseRepository() {
    private var authFirebase = FirebaseAuth.getInstance()
    suspend fun login (email:String, password: String): Resorces {
        var su = false
        var account: Account? = null;
        //Este  códido se ejecuta en un hilo especifico para operaciones entrada/salida (IO)
        withContext(Dispatchers.IO){
            try {

                val authResult: AuthResult = authFirebase.signInWithEmailAndPassword(email,password).await()
               // println("Email: $email  contraseña: $password, id ${authResult.user!!.uid}, username: ${authResult.additionalUserInfo!!.username} ")
                account = Account.create(authResult.user!!.uid,
                    Email(email),password,authResult.user!!.displayName, AccountState.VERIFIED)
                su = true;

            }catch (e: Exception){
                Resorces.Error(e)
                su = false;
                //println("Error en el catch: ${e.message}")
            }
            //Se ejecutara el codigo de consulta a FireBase. Que puede tardar mas de 5s y en ese
            //caso se obtiene el error ANR (Android Not Responding) porque puede bloquear la vista
        }
        if(su){
            //println("bien")
            return Resorces.Sucess(account)
        }else{
            //println("mal")
            return Resorces.Error(Exception("El password es incorrecto"))
        }

    }
}