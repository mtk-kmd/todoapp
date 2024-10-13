package com.example.todoapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var taskAdapter: TaskAdapter
    private val tasks = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        taskAdapter = TaskAdapter(tasks) { task ->
            deleteTask(task)
        }
        binding.recyclerView.adapter = taskAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Add task when button is clicked
        binding.buttonAdd.setOnClickListener {
            val taskName = binding.editTextTask.text.toString().trim()
            if (taskName.isNotEmpty()) {
                addTask(taskName)
                binding.editTextTask.text.clear()
            }
        }
    }

    private fun addTask(name: String) {
        val task = Task(name)
        tasks.add(task)
        taskAdapter.notifyItemInserted(tasks.size - 1)
    }

    private fun deleteTask(task: Task) {
        val index = tasks.indexOf(task)
        tasks.removeAt(index)
        taskAdapter.notifyItemRemoved(index)
    }
}
