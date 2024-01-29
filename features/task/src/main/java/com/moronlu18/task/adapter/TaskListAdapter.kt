package com.moronlu18.task.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.moronlu18.task.entity.Task
import com.moronlu18.taskFragment.R

class TaskListAdapter(
    val tasks: MutableList<Task>,
    private val onClick: (pos: Int, nav: Int) -> Unit
) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var title: TextView
        var customer: TextView
        var dateCreate: TextView
        var dateEnd: TextView
        var taskCV: CardView
        var ivEdit: ImageView
        var ivDelete: ImageView

        init {
            title = v.findViewById(R.id.tvTitleTaskList)
            customer = v.findViewById(R.id.tvCustomerTaskList)
            dateCreate = v.findViewById(R.id.tvDCreateTaskList)
            dateEnd = v.findViewById(R.id.tvDEndTaskList)
            taskCV = v.findViewById(R.id.cvTaskAdapter)
            ivEdit = v.findViewById(R.id.ivEdit)
            ivDelete = v.findViewById(R.id.ivDelete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_task_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        holder.title.text = task.title
        holder.customer.text = task.customer.getFullName()
        holder.dateCreate.text = task.createdDate
        holder.dateEnd.text = task.endDate

        holder.taskCV.setOnClickListener {
            onClick(position, 0)
        }
        holder.ivDelete.setOnClickListener {
            tasks.removeAt(position)
            notifyDataSetChanged()
            notifyItemRemoved(position)
        }
        holder.ivEdit.setOnClickListener {
            onClick(position, 1)
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

}