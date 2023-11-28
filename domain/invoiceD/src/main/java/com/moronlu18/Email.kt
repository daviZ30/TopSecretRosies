package com.moronlu18

import java.util.regex.Pattern

class Email(val value: String) {
    private val pattern = Pattern.compile("")

    init {
        if (!pattern.matcher(value).matches())
            throw
    }
}