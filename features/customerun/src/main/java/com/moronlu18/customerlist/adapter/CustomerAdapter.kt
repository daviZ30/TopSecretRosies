package com.moronlu18.customerlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moronlu18.customerlist.ui.Cliente
import com.moronlu18.customerlist.R

class CustomerAdapter(val clientes : List<Cliente>):
    RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {
    inner class CustomerViewHolder(customerView: View) : RecyclerView.ViewHolder(customerView) {
        fun  bind(customer: Cliente){
            itemView.findViewById<TextView>(R.id.txtnombre_customer_list).text = "${customer.nombre}"
            itemView.findViewById<TextView>(R.id.txtapellidos_customer_list).text = "${customer.apellidos}"
            itemView.findViewById<TextView>(R.id.txtemail_customer_list).text = "${customer.email}"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return CustomerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return clientes.size
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val item = clientes[position]
        holder.bind(item)
    }
}