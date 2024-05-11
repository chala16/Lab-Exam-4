package com.example.it22338952labexam4.repository

import android.app.DownloadManager.Query
import android.provider.ContactsContract.CommonDataKinds.Note
import com.example.it22338952labexam4.database.TodoDatabase
import com.example.it22338952labexam4.model.Todo

class TodoRepository(private val db: TodoDatabase) {

    suspend fun insertTodo(todo: Todo) = db.getTodoDao().insertTodo(todo)
    suspend fun deleteTodo(todo: Todo) = db.getTodoDao().deleteTodo(todo)
    suspend fun updateTodo(todo: Todo) = db.getTodoDao().updateTodo(todo)

    fun getAllTodoes() = db.getTodoDao().getAllTodoes()
    fun searchTodo(query: String?) = db.getTodoDao().searchTodo(query)

}