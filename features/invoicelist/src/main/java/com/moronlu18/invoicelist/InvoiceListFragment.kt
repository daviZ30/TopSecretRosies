package com.moronlu18.invoicelist

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.sql.Time




data class Articulo(val nombre:String,val precio:Double)
data class Factura(val Cliente:String,val FeEmision:String,val FeVencimiento:String,val Articulos:List<Articulo>,val total:Double)

class InvoiceListFragment : Fragment() {

    val facturas = listOf<Factura>(
        Factura("Antonio","10/10/2020","20/20/2023", listOf(
            Articulo("Patata",20.2),
            Articulo("Zanahoria",2.2)),50.2),
        Factura("Marta","10/10/2000","20/20/2023", listOf(
            Articulo("Zapato",20.2),
            Articulo("Cordón",2.2)),40.2),
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var vista:View = inflater.inflate(R.layout.fragment_invoice_list, container, false)

        val rc = vista.findViewById<RecyclerView>(R.id.rvInvoiceList)
        rc.adapter = AdaptadorFacturas(facturas)
        rc.layoutManager = LinearLayoutManager(context)

        // Inflate the layout for this fragment
        return vista
    }


}