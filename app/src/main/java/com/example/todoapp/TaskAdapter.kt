package com.example.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onDelete: (Task) -> Unit,
    private val databaseHelper: DatabaseHelper
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // ViewHolder class to hold views for each item
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewTask)
        val buttonDelete: ImageButton = itemView.findViewById(R.id.buttonDelete)
    }

    // Inflates the item layout and creates ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false) // Inflate item layout
        return TaskViewHolder(view)
    }

    // Binds data to the views in the ViewHolder
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.textViewName.text = task.name

        // Change text style based on completion status
        holder.textViewName.paint.isStrikeThruText = task.isCompleted

        // Set up delete button listener
        holder.buttonDelete.setOnClickListener {
            onDelete(task)
        }

        // Handle toggle completion status
        holder.itemView.setOnClickListener {
            task.isCompleted = !task.isCompleted // Toggle completion
            updateTaskInDb(task) // Call updateTaskInDb directly
            notifyItemChanged(position) // Notify adapter of change
        }
    }

    private fun updateTaskInDb(task: Task) {
        databaseHelper.updateTask(task) // Update task in database
    }

    // Returns the total number of items in the list
    override fun getItemCount(): Int {
        return tasks.size
    }
}
