package com.moronlu18.invoice.ui.firebase

import java.util.regex.Pattern

class Email(val value: String) {
    private val pattern = Pattern.compile("^\\S+@\\S+\\.\\S+")

    init {
        if (!pattern.matcher(value).matches()){
            throw AccountException.InvalidEmailFormat()
        }
    }
}