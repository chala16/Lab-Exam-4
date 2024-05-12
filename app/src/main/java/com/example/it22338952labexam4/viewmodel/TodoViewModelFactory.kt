package com.example.it22338952labexam4.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.it22338952labexam4.repository.TodoRepository

// Factory class for creating instances of TodoViewModel
class TodoViewModelFactory(val app: Application, private val todoRepository: TodoRepository) : ViewModelProvider.Factory{

    // Create a new instance of the specified ViewModel class
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Return a new instance of TodoViewModel with the provided Application and TodoRepository
        return TodoViewModel(app, todoRepository) as T
    }
}
