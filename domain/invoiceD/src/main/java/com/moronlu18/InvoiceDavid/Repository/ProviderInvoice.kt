package com.moronlu18.invoice.Repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.moronlu18.customer.entity.Cliente
import com.moronlu18.invoice.entity.Factura
import com.moronlu18.item.repository.ItemRepository

class ProviderInvoice private constructor() {
    companion object {
        val datasetFactura: MutableList<Factura> = setUpDataSetFactura()



        //@RequiresApi(Build.VERSION_CODES.O)
        private fun setUpDataSetFactura(): MutableList<Factura> {
            var dataset: MutableList<Factura> = ArrayList()
           // val format = SimpleDateFormat("yyyy-MM-dd")
            //val date = format.parse(dateString)
            val articulos = ItemRepository().getItemList()
            dataset.add(
                Factura(
                    1,
                    Cliente(
                        1,
                        "Juanlu",
                        "Cabrera Jimenez",
                        "carnetaadspjf@gmail.com",
                        6824556414,
                        "Málaga",
                        "Calle Leonora n46"
                    ),  "10/10/2000", "20/20/2023", mutableListOf(
                        //Articulo("Zapato", 20.2),
                        //Articulo("Cordón", 2.2)
                        articulos[0],
                        articulos[1],

                        )
                )
            )
            dataset.add(
                Factura(
                    2,
                    Cliente(
                        1,
                        "Juanlu",
                        "Cabrera Jimenez",
                        "carnetaadspjf@gmail.com",
                        6824556414,
                        "Málaga",
                        "Calle Leonora n46"
                    ), "10/10/2020", "20/20/2023", mutableListOf(
                        articulos[2],
                        articulos[3],
                    )
                )
            )
            return dataset
        }
    }
}