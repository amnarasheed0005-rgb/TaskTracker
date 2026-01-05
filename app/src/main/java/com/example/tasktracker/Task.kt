package com.example.tasktracker

data class Task(
    val title: String,
    val subtitle: String,
    val dueDate: String, // For simplicity, using String for now
    var status: Status
)

enum class Status {
    TODO,
    IN_PROGRESS,
    DONE
}