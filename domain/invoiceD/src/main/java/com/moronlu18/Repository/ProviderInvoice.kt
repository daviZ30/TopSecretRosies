package com.moronlu18.Repository

import com.moronlu18.entity.Articulo
import com.moronlu18.entity.Factura

class ProviderInvoice private constructor() {
    companion object {
        val datasetFactura: MutableList<Factura> = setUpDataSetFactura()
        val datasetArticulo: MutableList<Articulo> = setUpDataSetArticulo()

        private fun setUpDataSetArticulo(): MutableList<Articulo> {
            var dataset: MutableList<Articulo> = ArrayList()
            dataset.add(
                Articulo("Mesa", 222.2),
            )
            dataset.add(
                Articulo("Portaminas", 1.0)
            )
            return dataset
        }

        private fun setUpDataSetFactura(): MutableList<Factura> {
            var dataset: MutableList<Factura> = ArrayList()
            dataset.add(
                Factura(
                    "Marta", "10/10/2000", "20/20/2023", listOf(
                        Articulo("Zapato", 20.2),
                        Articulo("Cordón", 2.2)
                    ), 40.2
                )
            )
            dataset.add(
                Factura(
                    "Antonio", "10/10/2020", "20/20/2023", listOf(
                        Articulo("Patata", 20.2),
                        Articulo("Zanahoria", 2.2)
                    ), 50.2
                )
            )
            return dataset
        }
    }
}