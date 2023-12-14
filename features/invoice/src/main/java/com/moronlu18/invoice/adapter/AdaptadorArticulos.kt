package com.moronlu18.invoice.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moronlu18.invoiceFragment.R
import com.moronlu18.item.entity.item


class AdaptadorArticulos(
    val articulos: List<item>,
    val mostrar : Boolean,
    private val onDelete: (position: Int) -> Unit,
) : RecyclerView.Adapter<AdaptadorArticulos.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var nombre: TextView
        var precio: TextView
        var img: ImageView
        init {
            nombre = v.findViewById(R.id.tvInvoiceListaArticulosNombre)
            precio = v.findViewById(R.id.tvInvoiceListaArticulosPrecio)
            img = v.findViewById(R.id.imgEliminarArticulo)

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int

    ): AdaptadorArticulos.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.fila_articulos, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: AdaptadorArticulos.ViewHolder, position: Int) {
        val f = articulos[position]

        holder.nombre.text = f.name
        holder.precio.text = "${f.rate.toString()} €"
        if(mostrar){
            holder.img.visibility = View.GONE
        }else{
            holder.img.visibility = View.VISIBLE
        }
        holder.img.setOnClickListener{
            onDelete(position)
        }

    }

    override fun getItemCount(): Int {
        return articulos.size
    }
}