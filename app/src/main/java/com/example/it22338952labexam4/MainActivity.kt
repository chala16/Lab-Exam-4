package com.example.it22338952labexam4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.it22338952labexam4.database.TodoDatabase
import com.example.it22338952labexam4.repository.TodoRepository
import com.example.it22338952labexam4.viewmodel.TodoViewModel
import com.example.it22338952labexam4.viewmodel.TodoViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var todoViewModel: TodoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
    }

    private fun setupViewModel(){
        val todoRepository = TodoRepository(TodoDatabase(this))
        val viewModelProviderFactory = TodoViewModelFactory(application, todoRepository)
        todoViewModel = ViewModelProvider(this, viewModelProviderFactory)[TodoViewModel::class.java]
    }
}