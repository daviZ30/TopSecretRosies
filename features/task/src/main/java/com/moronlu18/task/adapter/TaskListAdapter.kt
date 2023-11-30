package com.moronlu18.task.adapter

import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.moronlu18.taskFragment.R
import com.moronlu18.task.entity.Task

class TaskListAdapter(
    val tasks: MutableList<Task>,
    private val onClick: (pos: Int, nav: Int) -> Unit
) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var titulo: TextView
        var cliente: TextView
        var fecha: TextView
        var hora: TextView
        var tarea: CardView
        var ivEditar: ImageView
        var ivEliminar: ImageView

        init {
            titulo = v.findViewById(R.id.tvTituloTaskList)
            cliente = v.findViewById(R.id.tvClienteTaskList)
            fecha = v.findViewById(R.id.tvFStartTaskList)
            hora = v.findViewById(R.id.tvFEndTaskList)
            tarea = v.findViewById(R.id.cvTaskAdapter)
            ivEditar = v.findViewById(R.id.ivEdit)
            ivEliminar = v.findViewById(R.id.ivDelete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_task_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        holder.titulo.text = task.title
        holder.cliente.text = task.nameCustomer
        holder.fecha.text = task.createdDate
        holder.hora.text = task.endDate
        holder.tarea.setOnClickListener {
            onClick(position, 0)
        }
        holder.ivEliminar.setOnClickListener {
            tasks.removeAt(position)
            notifyDataSetChanged()
            //notifyItemRemoved(position)
        }
        holder.ivEditar.setOnClickListener {
            onClick(position, 1)
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

}