package com.example.tasktracker

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LocalStorageHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        private const val PREFS_NAME = "TaskTrackerPrefs"
        private const val KEY_USERS = "users"
        private const val KEY_TASKS = "tasks"
    }

    /**
     * Save a new user to local storage
     * @return true if user was saved successfully, false if user already exists
     */
    fun saveUser(user: User): Boolean {
        val users = getAllUsers()
        
        // Check if user with this email already exists
        if (users.any { it.email.equals(user.email, ignoreCase = true) }) {
            return false
        }
        
        users.add(user)
        val usersJson = gson.toJson(users)
        sharedPreferences.edit().putString(KEY_USERS, usersJson).apply()
        return true
    }

    /**
     * Get all users from local storage
     */
    fun getAllUsers(): MutableList<User> {
        val usersJson = sharedPreferences.getString(KEY_USERS, null)
        return if (usersJson != null) {
            val type = object : TypeToken<MutableList<User>>() {}.type
            gson.fromJson(usersJson, type) ?: mutableListOf()
        } else {
            mutableListOf()
        }
    }

    /**
     * Validate user credentials
     * @return true if user exists and password matches, false otherwise
     */
    fun validateUser(email: String, password: String): Boolean {
        val users = getAllUsers()
        return users.any { 
            it.email.equals(email, ignoreCase = true) && it.password == password 
        }
    }

    /**
     * Get user by email
     */
    fun getUserByEmail(email: String): User? {
        val users = getAllUsers()
        return users.find { it.email.equals(email, ignoreCase = true) }
    }

    /**
     * Save all tasks to local storage
     */
    fun saveTasks(tasks: List<Task>) {
        val tasksJson = gson.toJson(tasks)
        sharedPreferences.edit().putString(KEY_TASKS, tasksJson).apply()
    }

    /**
     * Get all tasks from local storage
     */
    fun getAllTasks(): MutableList<Task> {
        val tasksJson = sharedPreferences.getString(KEY_TASKS, null)
        return if (tasksJson != null) {
            val type = object : TypeToken<MutableList<Task>>() {}.type
            gson.fromJson(tasksJson, type) ?: mutableListOf()
        } else {
            mutableListOf()
        }
    }

    /**
     * Add a new task to local storage
     */
    fun addTask(task: Task) {
        val tasks = getAllTasks()
        tasks.add(task)
        saveTasks(tasks)
    }

    /**
     * Update tasks in local storage
     */
    fun updateTasks(tasks: List<Task>) {
        saveTasks(tasks)
    }
}

