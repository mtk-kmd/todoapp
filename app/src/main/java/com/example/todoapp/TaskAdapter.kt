package com.example.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onDelete: (Task) -> Unit
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
        holder.textViewName.text = task.name // Set task name

        // Set up delete button listener
        holder.buttonDelete.setOnClickListener {
            onDelete(task) // Call the delete function passed from MainActivity
        }
    }

    // Returns the total number of items in the list
    override fun getItemCount(): Int {
        return tasks.size
    }
}
