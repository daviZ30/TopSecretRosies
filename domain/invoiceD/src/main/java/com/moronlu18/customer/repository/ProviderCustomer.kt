package com.moronlu18.customer.repository

import com.moronlu18.customer.entity.Cliente
import com.moronlu18.invoice.ui.firebase.Email
import com.moronlu18.invoice.ui.firebase.network.Resorces

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
         fun login (
             email: String,
             nombre: String,
             apellidos: String = "",
             telfono: String= "",
             ciudad: String= "",
             direccion: String= ""
         ): Resorces {
             var cliente:Cliente
             try {
                 cliente= Cliente(datasetCustomer.size+1,nombre,apellidos, Email(email),telfono,ciudad,direccion)
                 return Resorces.Sucess<Cliente>(cliente)
             }catch (e :Exception){
                 return Resorces.Error(e)
             }

         }
    }
}