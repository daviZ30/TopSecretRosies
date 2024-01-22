package com.moronlu18.accountsignup.ui

import Resorces

sealed class SignUpState {
    object EmailEmptyError : SignUpState()
    data object EmailFormatError : SignUpState()
    data object PasswordEmptyError : SignUpState()
    data object PasswordFormatError : SignUpState()
    data class Success(var account: Resorces) : SignUpState()
    data class AuthencationError(var message:String):SignUpState()

    //Se debe crear una clase que contiene un valor booleano que indica si se uestra el Progresvar
    data class Loading(var value: Boolean) : SignUpState()

}