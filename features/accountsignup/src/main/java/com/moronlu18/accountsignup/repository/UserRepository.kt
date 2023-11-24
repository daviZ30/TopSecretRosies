package com.moronlu18.accountsignup.repository

import com.moronlu18.accountsignup.network.Resorces
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.Exception

class UserRepository {
    companion object{
        val dataset:MutableList<User> = setUpDataSetUser()


        private fun setUpDataSetUser(): MutableList<User> {
            var dataset: MutableList<User> = ArrayList()
            dataset.add(User("David", "Zambrana", "davidzambrana@gmail.com"))
            dataset.add(User("Pablo", "Zambrana", "pablo@gmail.com"))
            dataset.add(User("Oriol", "Romeu", "barca@gmail.com"))
            dataset.add(User("Ansu", "Fati", "dalecaña@gmail.com"))

            return dataset
        }

        /**
         * La funcion que se pregunta a Firebase /Rose (SqlLite) por el usuario
         */
        suspend fun login (email:String, password: String):Resorces{
            //Este  códido se ejecuta en un hilo especifico para operaciones entrada/salida (IO)
            withContext(Dispatchers.IO){
                delay(3000)
                //Se ejecutara el codigo de consulta a FireBase. Que puede tardar mas de 5s y en ese
                //caso se obtiene el error ANR (Android Not Responding) porque puede bloquear la vista
            }
            return Resorces.Error(Exception("El password es incorrecto"))
        }
    }
}