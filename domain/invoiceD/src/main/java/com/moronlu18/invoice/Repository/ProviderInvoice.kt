package com.moronlu18.invoice.Repository

import com.moronlu18.invoice.entity.Articulo
import com.moronlu18.invoice.entity.Factura

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
                    "Marta", "marta21@gmail.com",568558558,"10/10/2000", "20/20/2023", listOf(
                        Articulo("Zapato", 20.2),
                        Articulo("Cordón", 2.2)
                    )
                )
            )
            dataset.add(
                Factura(
                    "Antonio", "antonio217@gmail.com",668589858,"10/10/2020", "20/20/2023", listOf(
                        Articulo("Patata", 20.2),
                        Articulo("Zanahoria", 2.2)
                    )
                )
            )
            return dataset
        }
    }
}