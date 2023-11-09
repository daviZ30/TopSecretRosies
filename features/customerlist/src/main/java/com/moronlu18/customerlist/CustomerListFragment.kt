package com.moronlu18.customerlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class Cliente(val nombre:String,val apellidos:String,val email:String)
class CustomerListFragment : Fragment() {
    val clientes = listOf<Cliente>(
        Cliente("Alex","Carnero","carnero@gmail.com"),
        Cliente("Alex","Carnero","carnero@gmail.com"),
        Cliente("Alex","Carnero","carnero@gmail.com"))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var vista:View= inflater.inflate(R.layout.fragment_customer__list_, container, false)
        val rc = vista.findViewById<RecyclerView>(R.id.listcustomer)
        rc.adapter = CustomerAdapter(clientes)
        rc.layoutManager= LinearLayoutManager(context)
        return vista
    }

}