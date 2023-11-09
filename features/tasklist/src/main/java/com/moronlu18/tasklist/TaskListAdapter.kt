package com.moronlu18.tasklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskListAdapter(val tasks:List<Task>): RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    class ViewHolder(v: View):RecyclerView.ViewHolder(v){
        var titulo:TextView
        var cliente:TextView
        var fecha:TextView
        var hora:TextView
        init {
            titulo = v.findViewById(R.id.tvTituloTaskList)
            cliente = v.findViewById(R.id.tvClienteTaskList)
            fecha = v.findViewById(R.id.tvFechaTaskList)
            hora = v.findViewById(R.id.tvHoraTaskList)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_task_list, parent, false)
        return  ViewHolder(v)
    }

    override fun onBindViewHolder(holder: TaskListAdapter.ViewHolder, position: Int) {
        val task = tasks[position]
        holder.titulo.text = task.titulo
        holder.cliente.text = task.cliente
        holder.fecha.text = task.fecha
        holder.hora.text = task.hora
    }
    override fun getItemCount(): Int {
        return tasks.size
    }

}