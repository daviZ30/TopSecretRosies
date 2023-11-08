package com.moronlu18.invoicecreation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class Articulo(val nombre:String,val precio:Double)
class InvoiceCreationFragment : Fragment() {

    val articulos = listOf<Articulo>(
        Articulo("Mesa",222.2),
        Articulo("Portaminas",1.0)
    )
    /*private  var _binding : =null ;
    private val binding
        get() = _binding!!*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var vista:View = inflater.inflate(R.layout.fragment_invoice_creation, container, false)

        val rc = vista.findViewById<RecyclerView>(R.id.rvInvoiceArticulos)
        rc.adapter = AdaptadorArticulos(articulos)
        rc.layoutManager = LinearLayoutManager(context)

        // Inflate the layout for this fragment
        return vista
    }


}