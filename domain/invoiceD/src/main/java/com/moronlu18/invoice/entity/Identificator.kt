package com.moronlu18.invoice.entity

abstract class Identificator(var id : Int) : Comparable<Identificator>{
    override fun compareTo(other: Identificator): Int {
        return id.compareTo(other.id)
    }
}