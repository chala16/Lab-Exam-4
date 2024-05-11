package com.example.it22338952labexam4.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.it22338952labexam4.repository.TodoRepository

class TodoViewModelFactory(val app: Application, private val todoRepository: TodoRepository) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TodoViewModel(app, todoRepository) as T
    }
}
