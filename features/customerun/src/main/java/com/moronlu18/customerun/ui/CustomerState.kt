package com.moronlu18.customerun.ui

import com.moronlu18.invoice.ui.firebase.network.Resorces

sealed class CustomerState {
    data object EmailEmtyError : CustomerState()
    data object EmailFormatError : CustomerState()
    data object NombreEmtyError : CustomerState()
    data object ApellidosEmtyError : CustomerState()

    data object ContactoFormatError : CustomerState()
    data object ReferencedCustomerError: CustomerState()
    data class Success(var cliente: Resorces) : CustomerState()
    data class AuthencationError(var message:String):CustomerState()
    data class Loading(var value: Boolean) : CustomerState()


}