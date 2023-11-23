package com.moronlu18.accountsignup.network

import java.lang.Exception

/**
 * Esta ckase sellada representa la respuesta de un servicio, APP REST, firebase donde se declara la clase Error que almacenara los
 * errores de la infraestructura y el caso de exicto de una coleccion de dato de tipo genético
 */
sealed class Resorces {
   // data class Sucess<T, E>(var data: T, var settings: E):Resorces()
   data class Sucess<T>(var data: Collection<T>):Resorces()
    data class Error(var exception: Exception) : Resorces()
}