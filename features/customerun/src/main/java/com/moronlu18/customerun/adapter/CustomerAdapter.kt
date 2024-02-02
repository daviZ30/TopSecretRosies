package com.moronlu18.customerun.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moronlu18.customer.entity.Customer
import com.moronlu18.customerun.R


class CustomerAdapter(val customers : MutableList<Customer>, private val onItemClick: (position:Int, nav:Int) -> Unit, private val onDelete: (position: Int) -> Unit,):
    RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {
     class CustomerViewHolder(customerView: View) : RecyclerView.ViewHolder(customerView) {
        fun  bind(customer: Customer){
            itemView.findViewById<TextView>(R.id.txtnombre_customer_list).text = "${customer.nombre}"
            itemView.findViewById<TextView>(R.id.txtapellidos_customer_list).text = "${customer.apellidos}"
            itemView.findViewById<TextView>(R.id.txtemail_customer_list).text = "${customer.email.value}"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return CustomerViewHolder(view)
    }


    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val item = customers[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{
            onItemClick.invoke(position,0)
        }
       holder.itemView.findViewById<ImageButton>(R.id.btndelete).setOnClickListener {
           onDelete(position)
       }
        holder.itemView.findViewById<ImageButton>(R.id.btnedit).setOnClickListener {
            onItemClick.invoke(position,1)

        }
    }
    override fun getItemCount(): Int {
        return customers.size
    }

}