package com.moronlu18.customer.repository

import com.moronlu18.customer.entity.Cliente
import com.moronlu18.customer.entity.CustomerId
import com.moronlu18.invoice.ui.firebase.Email

class ProviderCustomer private constructor() {


    companion object{
        val datasetCustomer: MutableList<Cliente> = setUpDateSetCliente()
        private  var clientescreados:Int = datasetCustomer.size+1;

        private fun setUpDateSetCliente(): MutableList<Cliente> {
            var dataset : MutableList<Cliente> = ArrayList()
            dataset.add(Cliente(CustomerId(1),"Juanlu","Cabrera Jimenez", Email("carnetaadspjf@gmail.com"),"6824556414","Málaga","Calle Leonora n46"))
            dataset.add(Cliente(CustomerId(2),"Antonio","Urquiza FAlle",Email("carnetaadspjf@gmail.com"),"6846556414","Málaga","Calle Leonora n46"))
            dataset.add(Cliente(CustomerId(3),"Alex","Carnero Tapia",
                Email("carnetaadspjf@gmail.com"),"6864646414","Málaga","Calle Leonora n46"))
            return dataset
        }
        public fun GetCliente(id:Int):Cliente?{
            datasetCustomer.forEach {
                if(it.id.value == id){
                    return it;
                }
            }
            return null
        }
        fun  create(cliente: Cliente,position: Int?){
            if (position == null){
                datasetCustomer.add(cliente)
                clientescreados++
            }
            else{
                datasetCustomer.add(position,cliente)
            }

        }
        fun getClientesCreados():Int{
            return clientescreados
        }


    }
}