package com.moronlu18

sealed class AccountException(message: String = ""): RuntimeException(message) {
    class InvalidEmailFormat:AccountException("Email con formato inválido")
}