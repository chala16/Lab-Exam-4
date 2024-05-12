package com.example.it22338952labexam4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.it22338952labexam4.database.TodoDatabase
import com.example.it22338952labexam4.repository.TodoRepository
import com.example.it22338952labexam4.viewmodel.TodoViewModel
import com.example.it22338952labexam4.viewmodel.TodoViewModelFactory

// Main activity of the application
class MainActivity : AppCompatActivity() {

    lateinit var todoViewModel: TodoViewModel
    // Called when the activity is starting
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Setup ViewModel
        setupViewModel()
    }

    // Setup ViewModel for managing Todo-related data
    private fun setupViewModel(){
        // Create TodoRepository with TodoDatabase
        val todoRepository = TodoRepository(TodoDatabase(this))
        // Create TodoViewModelFactory
        val viewModelProviderFactory = TodoViewModelFactory(application, todoRepository)
        // Initialize TodoViewModel using ViewModelProvider
        todoViewModel = ViewModelProvider(this, viewModelProviderFactory)[TodoViewModel::class.java]
    }
}