package com.moronlu18.item.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moronlu18.item.item
import com.moronlu18.itemcreation.R

class ItemAdapter(private val itemList: List<item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: item) {
            itemView.findViewById<TextView>(R.id.tvIdCard).text = "${item.id}"
            itemView.findViewById<TextView>(R.id.tvNameCard).text = "${item.name}"
            itemView.findViewById<TextView>(R.id.tvRateCard).text = "${item.rate}"
            itemView.findViewById<TextView>(R.id.tvTypeCard).text = "${item.type}"
            itemView.findViewById<TextView>(R.id.tvDescriptionCard).text = "${item.description}"
            itemView.findViewById<TextView>(R.id.tvIsTaxable).text = "${item.isTaxable}"
        }
    }
}