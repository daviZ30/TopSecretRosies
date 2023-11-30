package com.moronlu18


import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.moronlu18.invoice.ui.firebase.Account
import com.moronlu18.invoice.ui.firebase.AccountState
import com.moronlu18.invoice.ui.firebase.Email
import com.moronlu18.invoice.ui.firebase.network.Resorces
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class AuthDirebaseRepository() {
    private var authFirebase = FirebaseAuth.getInstance()
    suspend fun login (email:String, password: String): Resorces {
        var su = false
        var account: Account? = null;
        var resultado: Resorces? = null
        //Este  códido se ejecuta en un hilo especifico para operaciones entrada/salida (IO)
        withContext(Dispatchers.IO) {
            try {

                val authResult: AuthResult =
                    authFirebase.signInWithEmailAndPassword(email, password).await()
                // println("Email: $email  contraseña: $password, id ${authResult.user!!.uid}, username: ${authResult.additionalUserInfo!!.username} ")
                account = Account.create(
                    authResult.user!!.uid,
                    Email(email), password, authResult.user!!.displayName, AccountState.VERIFIED
                )
                resultado = Resorces.Sucess(account)
            } catch (e: Exception) {
                resultado = Resorces.Error(e)
            }
            //Se ejecutara el codigo de consulta a FireBase. Que puede tardar mas de 5s y en ese
            //caso se obtiene el error ANR (Android Not Responding) porque puede bloquear la vista
        }

        return resultado!!;
    }
}