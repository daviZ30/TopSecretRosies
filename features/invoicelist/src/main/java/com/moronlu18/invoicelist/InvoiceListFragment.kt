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


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Articulo(val nombre:String,val precio:Double)
data class Factura(val Cliente:String,val FeEmision:String,val FeVencimiento:String,val Articulos:List<Articulo>,val total:Double)


/**
 * A simple [Fragment] subclass.
 * Use the [InvoiceListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InvoiceListFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

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
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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