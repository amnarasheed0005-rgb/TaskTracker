package com.example.tasktracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {

    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var addTaskButton: FloatingActionButton
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var localStorageHelper: LocalStorageHelper
    private val tasks = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        
        supportActionBar?.title = "Task Tracker"

        localStorageHelper = LocalStorageHelper(this)
        tasksRecyclerView = findViewById(R.id.tasksRecyclerView)
        addTaskButton = findViewById(R.id.addTaskButton)

        taskAdapter = TaskAdapter(tasks) { saveTasksToStorage() }
        tasksRecyclerView.adapter = taskAdapter
        tasksRecyclerView.layoutManager = LinearLayoutManager(this)

        // Setup drag and drop functionality
        setupDragAndDrop()

        // Load tasks from local storage
        loadTasksFromStorage()

        addTaskButton.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun setupDragAndDrop() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            0
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition

                // Validate positions
                if (fromPosition == RecyclerView.NO_POSITION || toPosition == RecyclerView.NO_POSITION) {
                    return false
                }

                if (fromPosition < toPosition) {
                    // Moving down
                    for (i in fromPosition until toPosition) {
                        val temp = tasks[i]
                        tasks[i] = tasks[i + 1]
                        tasks[i + 1] = temp
                    }
                } else {
                    // Moving up
                    for (i in fromPosition downTo toPosition + 1) {
                        val temp = tasks[i]
                        tasks[i] = tasks[i - 1]
                        tasks[i - 1] = temp
                    }
                }

                taskAdapter.notifyItemMoved(fromPosition, toPosition)
                saveTasksToStorage()
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Not used for drag and drop
            }

            override fun isLongPressDragEnabled(): Boolean {
                return true
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView)
    }

    private fun loadTasksFromStorage() {
        val savedTasks = localStorageHelper.getAllTasks()
        tasks.clear()
        tasks.addAll(savedTasks)
        taskAdapter.notifyDataSetChanged()
    }

    private fun saveTasksToStorage() {
        localStorageHelper.updateTasks(tasks)
    }

    private fun showAddTaskDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.add_task_dialog, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogView)

        val titleEditText = dialogView.findViewById<EditText>(R.id.taskTitleEditText)
        val subtitleEditText = dialogView.findViewById<EditText>(R.id.taskSubtitleEditText)
        val datePicker = dialogView.findViewById<DatePicker>(R.id.datePicker)
        val statusSpinner = dialogView.findViewById<Spinner>(R.id.statusSpinner)
        val saveTaskButton = dialogView.findViewById<Button>(R.id.saveTaskButton)

        val statusAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Status.values())
        statusSpinner.adapter = statusAdapter

        saveTaskButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val subtitle = subtitleEditText.text.toString()
            val day = datePicker.dayOfMonth
            val month = datePicker.month + 1
            val year = datePicker.year
            val dueDate = "$year-$month-$day"
            val status = statusSpinner.selectedItem as Status

            if (title.isNotEmpty()) {
                val newTask = Task(title, subtitle, dueDate, status)
                tasks.add(newTask)
                taskAdapter.notifyItemInserted(tasks.size - 1)
                saveTasksToStorage()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }
}