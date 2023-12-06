package com.moronlu18.invoice.ui

import com.moronlu18.invoice.ui.firebase.network.Resorces

sealed class InvoiceState {
    data object idFacturaEmtyError : InvoiceState()
    data object idClienteEmtyError : InvoiceState()
    data object idClienteInvalidError : InvoiceState()
    data object feEmiInvalidError : InvoiceState()
    data object feVenInvalidError : InvoiceState()
    data object feEmiEmtyError : InvoiceState()
    data object feVenEmtyError : InvoiceState()

    data object dateInvalidError : InvoiceState()

    data object Success : InvoiceState()


}