package com.moronlu18.task.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moronlu18.task.entity.Task
import com.moronlu18.taskFragment.databinding.RowTaskListBinding


class TaskListAdapter(
    private val onClick: (pos: Int, nav: Int) -> Unit
) : ListAdapter<Task, TaskListAdapter.ViewHolder>(TASK_COMPARATOR) {

    inner class ViewHolder(var binding: RowTaskListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task, position: Int) {
            with(binding) {
                tvTitleTaskList.text = task.title
                tvCustomerTaskList.text = task.customer.getFullName()
                tvDCreateTaskList.text = task.createdDate
                tvDEndTaskList.text = task.endDate
                cvTaskAdapter.setOnClickListener {
                    onClick(position, 0)
                }
                ivDelete.setOnClickListener {
                    //tasks.removeAt(position)
                    notifyDataSetChanged()
                    notifyItemRemoved(position)
                }
                ivEdit.setOnClickListener {
                    onClick(position, 1)

                }
            }


            /*var title: TextView
        var customer: TextView
        var dateCreate: TextView
        var dateEnd: TextView
        var taskCV: CardView
        var ivEdit: ImageView
        var ivDelete: ImageView

        init {
            title = binding.tvTitleTaskList
            customer = binding.tvCustomerTaskList
            dateCreate =binding.tvDCreateTaskList
            dateEnd = binding.tvDEndTaskList
            taskCV = binding.cvTaskAdapter
            ivEdit = binding.ivEdit
            ivDelete = binding.ivDelete
        }*/
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(RowTaskListBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = getItem(position)

        holder.bind(task, position)

        /*val task = tasks[position]
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
        }*/
    }

    companion object {
        private val TASK_COMPARATOR = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.idTask == newItem.idTask
            }

        }
    }
}



