package com.example.todolist2


import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import java.time.format.DateTimeFormatter


/*import com.example.todolist2.TaskListAdapter.TaskViewHolder*/

class TaskListAdapter(private val listener: OnClickListener) :
    ListAdapter<Task, TaskListAdapter.TaskViewHolder>(TasksComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder.create(parent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, listener)
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /*   private val task_id: TextView = itemView.findViewById(R.id.task_id)*/
        private val task_name: TextView = itemView.findViewById(R.id.task_name)
        private val task_start_date: TextView = itemView.findViewById(R.id.task_start_date)
        private val task_end_date: TextView = itemView.findViewById(R.id.task_end_date)
        private val task_text: TextView = itemView.findViewById(R.id.task_text)
        private val task_icon: ImageView = itemView.findViewById(R.id.task_icon)
        private val task_priority: ImageView = itemView.findViewById(R.id.task_priority)
        private val task_localization: TextView = itemView.findViewById(R.id.task_localization)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(task: Task, listener: OnClickListener) {
            itemView.setOnLongClickListener {
                listener.deleteOnLongClick(task)
                true
            }

            task_name.text = task.task_name
            /*     task_id.text = task.id.toString()*/
            task_start_date.text =
                task.task_start_date!!.format(DateTimeFormatter.ofPattern("d/MMM/yyyy HH:mm"))
            task_end_date.text =
                task.task_end_date!!.format(DateTimeFormatter.ofPattern("d/MMM/yyyy HH:mm"))
            task_text.text = task.task_text
            task_localization.text = task.task_localization

            when (task.task_icon) {
                Images.CHECK -> task_icon.setImageResource(R.drawable.check2)
                Images.STAR -> task_icon.setImageResource(R.drawable.gwiazdka2)
                Images.CLEAN -> task_icon.setImageResource(R.drawable.clean)
                Images.CODE -> task_icon.setImageResource(R.drawable.code)
                Images.MONEY -> task_icon.setImageResource(R.drawable.money)
                Images.SHOOL -> task_icon.setImageResource(R.drawable.school)
            }

            when (task.task_priority) {
                "1" -> task_priority.setImageResource(R.drawable.niski)
                "2" -> task_priority.setImageResource(R.drawable.redni)
                "3" -> task_priority.setImageResource(R.drawable.wysoki)
                "4" -> task_priority.setImageResource(R.drawable.asap)
            }

            val localization_text: TextView = itemView.findViewById(R.id.task_localization_text)

            when (task.task_localization) {
                "1" -> {
                    localization_text.text = ""
                    task_localization.text = ""
                }
                "2" -> {
                    task_localization.text = "W SZKOLE"
                    localization_text.text = "lokalizacja: "
                }
                "3" -> {
                    task_localization.text = "W PRACY"
                    localization_text.text = "lokalizacja: "
                }
                "4" -> {
                    task_localization.text = "W DOMU"
                    localization_text.text = "lokalizacja: "
                }
                "5" -> {
                    task_localization.text = "NA DWORZE"
                    localization_text.text = "lokalizacja: "
                }
                "6" -> {
                    task_localization.text = "W SAMOCHODZIE"
                    localization_text.text = "lokalizacja: "
                }
            }

        }

        companion object {
            fun create(parent: ViewGroup): TaskViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return TaskViewHolder(view)
            }
        }
    }

    class TasksComparator : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.task_name == newItem.task_name
        }
    }
}

interface OnClickListener {
    fun deleteOnLongClick(task: Task)
}