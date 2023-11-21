package com.moronlu18.invoice.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moronlu18.invoice.ui.Factura
import com.moronlu18.invoicelist.R


class AdaptadorFacturas(val facturas:List<Factura>)
    : RecyclerView.Adapter<AdaptadorFacturas.ViewHolder>() {

    class ViewHolder(v: View):RecyclerView.ViewHolder(v){
        var cliente:TextView
        var FeEmision:TextView
        var FeVencimiento:TextView
        var NumArticulos:TextView
        var Total:TextView
        init {
            cliente = v.findViewById(R.id.txtLineaCliente)
            FeEmision = v.findViewById(R.id.txtLineaFeEmision)
            FeVencimiento = v.findViewById(R.id.txtLineaFeVencimiento)
            NumArticulos = v.findViewById(R.id.txtLineaNumArticulos)
            Total = v.findViewById(R.id.txtLineaTotal)
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.fila_facturas,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val f = facturas[position]
        holder.cliente.text = f.Cliente
        holder.Total.text = f.total.toString()
        holder.NumArticulos.text = f.Articulos.size.toString()
        holder.FeEmision.text = f.FeEmision
        holder.FeVencimiento.text = f.FeVencimiento
    }

    override fun getItemCount(): Int {
        return facturas.size
    }
}