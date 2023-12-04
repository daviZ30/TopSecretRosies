package com.moronlu18.invoice.ui

import com.moronlu18.invoice.ui.firebase.network.Resorces

sealed class InvoiceState {
    data object idFacturaEmtyError : InvoiceState()
    data object idClienteEmtyError : InvoiceState()
    data object Success : InvoiceState()


}