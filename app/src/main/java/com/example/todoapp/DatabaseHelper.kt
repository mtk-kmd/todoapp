package com.example.todoapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "todo.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_TASKS = "tasks"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_COMPLETED = "completed"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_TASKS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_COMPLETED INTEGER DEFAULT 0)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TASKS")
        onCreate(db)
    }

    // Insert a task into the database
    fun insertTask(task: Task) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, task.name)
            put(COLUMN_COMPLETED, if (task.isCompleted) 1 else 0)
        }
        db.insert(TABLE_TASKS, null, values)
        db.close()
    }

    // Retrieve all tasks from the database
    fun getAllTasks(): List<Task> {
        val tasks = mutableListOf<Task>()
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_TASKS", null)

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val isCompleted = cursor.getInt(cursor.getColumnIndex(COLUMN_COMPLETED)) == 1
                tasks.add(Task(name, isCompleted))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return tasks
    }

    // Update a task's completed status
    fun updateTask(task: Task) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, task.name)
            put(COLUMN_COMPLETED, if (task.isCompleted) 1 else 0)
        }
        db.update(TABLE_TASKS, values, "$COLUMN_NAME = ?", arrayOf(task.name))
        db.close()
    }

    // Delete a task from the database
    fun deleteTask(task: Task) {
        val db = writableDatabase
        db.delete(TABLE_TASKS, "$COLUMN_NAME = ?", arrayOf(task.name))
        db.close()
    }
}
