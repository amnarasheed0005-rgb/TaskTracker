package com.example.tasktracker

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onTaskChanged: () -> Unit = {}
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }

    override fun getItemCount() = tasks.size

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.taskTitleTextView)
        private val subtitleTextView: TextView = itemView.findViewById(R.id.taskSubtitleTextView)
        private val dueDateTextView: TextView = itemView.findViewById(R.id.dueDateTextView)
        private val statusIndicator: View = itemView.findViewById(R.id.statusIndicator)
        private val todoButton: MaterialButton = itemView.findViewById(R.id.todoButton)
        private val inProgressButton: MaterialButton = itemView.findViewById(R.id.inProgressButton)
        private val doneButton: MaterialButton = itemView.findViewById(R.id.doneButton)

        fun bind(task: Task) {
            titleTextView.text = task.title
            subtitleTextView.text = task.subtitle
            dueDateTextView.text = "Due: ${task.dueDate}"

            // Update status indicator color
            val statusColor = when (task.status) {
                Status.TODO -> Color.parseColor("#FF9800") // Orange
                Status.IN_PROGRESS -> Color.parseColor("#2196F3") // Blue
                Status.DONE -> Color.parseColor("#4CAF50") // Green
            }
            statusIndicator.setBackgroundColor(statusColor)

            // Reset all buttons to default state
            resetButtonStyles()

            // Highlight the active status button
            when (task.status) {
                Status.TODO -> {
                    todoButton.setBackgroundColor(Color.parseColor("#FFF3E0"))
                    todoButton.setTextColor(ColorStateList.valueOf(Color.parseColor("#E65100")))
                    todoButton.strokeColor = ColorStateList.valueOf(Color.parseColor("#FF9800"))
                    todoButton.strokeWidth = 2
                }
                Status.IN_PROGRESS -> {
                    inProgressButton.setBackgroundColor(Color.parseColor("#E3F2FD"))
                    inProgressButton.setTextColor(ColorStateList.valueOf(Color.parseColor("#1565C0")))
                    inProgressButton.strokeColor = ColorStateList.valueOf(Color.parseColor("#2196F3"))
                    inProgressButton.strokeWidth = 2
                }
                Status.DONE -> {
                    doneButton.setBackgroundColor(Color.parseColor("#E8F5E9"))
                    doneButton.setTextColor(ColorStateList.valueOf(Color.parseColor("#2E7D32")))
                    doneButton.strokeColor = ColorStateList.valueOf(Color.parseColor("#4CAF50"))
                    doneButton.strokeWidth = 2
                }
            }

            todoButton.setOnClickListener {
                task.status = Status.TODO
                notifyItemChanged(adapterPosition)
                onTaskChanged()
            }

            inProgressButton.setOnClickListener {
                task.status = Status.IN_PROGRESS
                notifyItemChanged(adapterPosition)
                onTaskChanged()
            }

            doneButton.setOnClickListener {
                task.status = Status.DONE
                notifyItemChanged(adapterPosition)
                onTaskChanged()
            }
        }

        private fun resetButtonStyles() {
            val defaultBgColor = Color.parseColor("#FFFFFF")
            val defaultTextColor = ColorStateList.valueOf(Color.parseColor("#757575"))
            val defaultStrokeColor = ColorStateList.valueOf(Color.parseColor("#BDBDBD"))

            todoButton.setBackgroundColor(defaultBgColor)
            todoButton.setTextColor(defaultTextColor)
            todoButton.strokeColor = defaultStrokeColor
            todoButton.strokeWidth = 1

            inProgressButton.setBackgroundColor(defaultBgColor)
            inProgressButton.setTextColor(defaultTextColor)
            inProgressButton.strokeColor = defaultStrokeColor
            inProgressButton.strokeWidth = 1

            doneButton.setBackgroundColor(defaultBgColor)
            doneButton.setTextColor(defaultTextColor)
            doneButton.strokeColor = defaultStrokeColor
            doneButton.strokeWidth = 1
        }
    }
}