package com.moronlu18.accountsignup.ui

sealed class SignUpState {
    object EmailEmptyError : SignUpState()
    data object EmailFormatError : SignUpState()
    data object PasswordEmptyError : SignUpState()
    data object PasswordFormatError : SignUpState()
    data object Success : SignUpState()

}