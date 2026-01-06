
ask Tracker (Mobile App)
Overview

Task Tracker is a simple mobile app developed in Kotlin for Android. It allows users to create, update, delete, and manage tasks locally on their devices. Tasks are saved using local storage (SharedPreferences or Room Database), meaning no internet connection or backend is needed.

Features

Add, update, and delete tasks

Mark tasks as completed

Set deadlines for tasks

Store tasks in local storage for persistence

Simple and intuitive user interface

Technologies Used

Language: Kotlin

Local Storage:

SharedPreferences (for simple key-value storage)

Room Database (for more complex, structured storage)

UI: Android XML layout files (for views)

Dependencies:

Room
 (optional, for structured data)

Kotlin Coroutines
 (optional for background tasks)

Installation
Prerequisites

Android Studio
 installed

A basic understanding of Android development (Activities, Views, and Layouts)

A physical or virtual Android device to test the app

Steps to Install

Clone the repository:

git clone https://github.com/yourusername/task-tracker-android.git


Open the project in Android Studio:

Launch Android Studio and open the cloned repository.

Build and run the project:

Connect your Android device or start an emulator.

Click on the Run button in Android Studio (the green triangle) to build and launch the app on your device.

The app should now be installed on your device or emulator.

Usage
1. Add a Task

Tap the "Add Task" button to create a new task.

Enter the task title, description, and deadline.

The task will be saved locally in storage.

2. Edit a Task

Tap on an existing task to edit its details.

Modify the title, description, or deadline.

The changes are saved immediately to local storage.

3. Delete a Task

Swipe left or right on a task to delete it.

Confirm the deletion, and the task will be removed from local storage.

4. Mark Task as Completed

Tap the checkbox next to the task to mark it as completed.

The task will be visually updated to indicate its completion status.

5. View All Tasks

All tasks are displayed in a list, sorted by date or priority.

You can filter or sort tasks by their completion status.

Local Storage Options
SharedPreferences (Simple Storage)

If you are using SharedPreferences, data is saved in simple key-value pairs. Ideal for saving a small amount of data like task titles or completion status.

Room Database (Structured Storage)

If you are using Room Database, data is saved in a structured way with Entities and DAOs, allowing for more complex querying and handling of tasks.

For example:

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val deadline: String
)

Example of Task Entity with Room:
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id:


A network error occurred. Please check your connection and try again. If this issue persists please contact us through our help center at help.openai.com.

Retry
