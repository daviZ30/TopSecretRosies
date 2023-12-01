package com.moronlu18.customer.repository

import com.moronlu18.customer.entity.Cliente
import com.moronlu18.invoice.ui.firebase.network.Resorces

class ProviderCustomer private constructor() {
    companion object{
        val datasetCustomer: MutableList<Cliente> = setUpDateSetCliente()

        private fun setUpDateSetCliente(): MutableList<Cliente> {
            var dataset : MutableList<Cliente> = ArrayList()
            dataset.add(Cliente(1,"Juanlu","Cabrera Jimenez","carnetaadspjf@gmail.com",6824556414,"Málaga","Calle Leonora n46"))
            dataset.add(Cliente(2,"Antonio","Urquiza FAlle","carnetaadspjf@gmail.com",6846556414,"Málaga","Calle Leonora n46"))
            dataset.add(Cliente(3,"Alex","Carnero Tapia","carnetaadspjf@gmail.com",6864646414,"Málaga","Calle Leonora n46"))
            return dataset
        }
         fun login (email: String, nombre: String, value: String): Resorces {
          /*   var cliente:Cliente
            try {
                cliente = Cliente()
            }catch (Exception ){
                return Resorces.Error()
            }*/
             return Resorces.Error(Exception("Error"))
         }
    }
}