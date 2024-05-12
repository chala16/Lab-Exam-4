package com.example.it22338952labexam4.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.it22338952labexam4.model.Todo
import com.example.it22338952labexam4.repository.TodoRepository
import kotlinx.coroutines.launch

// ViewModel class for managing Todo-related operations
class TodoViewModel(app: Application, private val todoRepository: TodoRepository): AndroidViewModel(app) {

    // Add a new Todo
    fun addTodo(todo: Todo) =
        viewModelScope.launch {
            todoRepository.insertTodo(todo)
        }
    // Delete a Todo
    fun deleteTodo(todo: Todo) =
        viewModelScope.launch {
            todoRepository.deleteTodo(todo)
        }
    // Update a Todo
    fun updateTodo(todo: Todo) =
        viewModelScope.launch {
            todoRepository.updateTodo(todo)
        }
    // Get all Todoes from the repository
    fun getAllTodoes() = todoRepository.getAllTodoes()

    // Search for Todoes by query
    fun searchTodo(query: String?) =
        todoRepository.searchTodo(query)
}