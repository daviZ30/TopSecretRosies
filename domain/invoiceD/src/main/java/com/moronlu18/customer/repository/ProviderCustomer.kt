package com.moronlu18.customer.repository

import com.moronlu18.customer.entity.Cliente
import com.moronlu18.invoice.ui.firebase.Email

class ProviderCustomer private constructor() {


    companion object{
        val datasetCustomer: MutableList<Cliente> = setUpDateSetCliente()

        private fun setUpDateSetCliente(): MutableList<Cliente> {
            var dataset : MutableList<Cliente> = ArrayList()
            dataset.add(Cliente(1,"Juanlu","Cabrera Jimenez", Email("carnetaadspjf@gmail.com"),"6824556414","Málaga","Calle Leonora n46"))
            dataset.add(Cliente(2,"Antonio","Urquiza FAlle",Email("carnetaadspjf@gmail.com"),"6846556414","Málaga","Calle Leonora n46"))
            dataset.add(Cliente(3,"Alex","Carnero Tapia",
                Email("carnetaadspjf@gmail.com"),"6864646414","Málaga","Calle Leonora n46"))
            return dataset
        }
        public fun GetCliente(id:Int):Cliente?{
            datasetCustomer.forEach {
                if(it.id == id){
                    return it;
                }
            }
            return null
        }


    }
}