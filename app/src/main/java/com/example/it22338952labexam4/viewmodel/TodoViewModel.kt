package com.example.it22338952labexam4.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.it22338952labexam4.model.Todo
import com.example.it22338952labexam4.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(app: Application, private val todoRepository: TodoRepository): AndroidViewModel(app) {

    fun addTodo(todo: Todo) =
        viewModelScope.launch {
            todoRepository.insertTodo(todo)
        }
    fun deleteTodo(todo: Todo) =
        viewModelScope.launch {
            todoRepository.deleteTodo(todo)
        }
    fun updateTodo(todo: Todo) =
        viewModelScope.launch {
            todoRepository.updateTodo(todo)
        }

    fun getAllTodoes() = todoRepository.getAllTodoes()

    fun searchTodo(query: String?) =
        todoRepository.searchTodo(query)
}