package com.moronlu18.invoice.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.moronlu18.entity.Factura
import com.moronlu18.invoiceFragment.R


class AdaptadorFacturas(
    val facturas: MutableList<Factura>,
    private val onClick: (position: Int) -> Unit
) : RecyclerView.Adapter<AdaptadorFacturas.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var cliente: TextView
        var FeEmision: TextView
        var FeVencimiento: TextView
        var NumArticulos: TextView
        var Total: TextView
        var card: CardView
        var eliminar : ImageView

        init {
            cliente = v.findViewById(R.id.txtLineaCliente)
            FeEmision = v.findViewById(R.id.txtLineaFeEmision)
            FeVencimiento = v.findViewById(R.id.txtLineaFeVencimiento)
            NumArticulos = v.findViewById(R.id.txtLineaNumArticulos)
            Total = v.findViewById(R.id.txtLineaTotal)
            card = v.findViewById(R.id.cvFactura)
            eliminar = v.findViewById(R.id.imgEliminar)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.fila_facturas, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val f = facturas[position]
        var precios = f.Articulos.map { it.precio }
        var SubTotal = precios.reduce { acc, ar -> acc + ar}
        holder.cliente.text = f.Cliente
        holder.Total.text = String.format("%.2f €",SubTotal + (SubTotal * 0.21))
        holder.NumArticulos.text = f.Articulos.size.toString()
        holder.FeEmision.text = f.FeEmision
        holder.FeVencimiento.text = f.FeVencimiento
        holder.card.setOnClickListener {
            onClick(position)
        }
        holder.eliminar.setOnClickListener{
            facturas.removeAt(position)
            notifyItemRemoved(position)

        }

    }

    override fun getItemCount(): Int {
        return facturas.size
    }
}