package com.moronlu18.accountsignup.ui

import android.accounts.Account

sealed class SignUpState {
    object EmailEmptyError : SignUpState()
    data object EmailFormatError : SignUpState()
    data object PasswordEmptyError : SignUpState()
    data object PasswordFormatError : SignUpState()
    data object Success  /*(var account: Account)*/ : SignUpState()
    data class AuthencationError(var message:String):SignUpState()

    //Se debe crear una clase que contiene un valor booleano que indica si se uestra el Progresvar
    data class Loading(var value: Boolean) : SignUpState()

}