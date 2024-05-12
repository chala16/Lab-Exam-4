package com.example.it22338952labexam4.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.example.it22338952labexam4.MainActivity
import com.example.it22338952labexam4.R
import com.example.it22338952labexam4.databinding.FragmentAddNoteBinding
import com.example.it22338952labexam4.model.Todo
import com.example.it22338952labexam4.viewmodel.TodoViewModel

// Fragment for adding a new note
class AddNoteFragment : Fragment(R.layout.fragment_add_note), MenuProvider {

    private var addNoteBinding: FragmentAddNoteBinding? = null
    private val binding get() = addNoteBinding!!
    private lateinit var todoesViewModel: TodoViewModel
    private lateinit var addTodoView: View

    // Inflate the layout for this fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addNoteBinding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set up menu provider
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // Initialize TodoViewModel
        todoesViewModel = (activity as MainActivity).todoViewModel
        addTodoView = view
    }

    // Save the new note
    private fun saveTodo(view: View){
        val todoTitle = binding.addTodoTitle.text.toString().trim()
        val todoDesc = binding.addTodoDesc.text.toString().trim()

        if (todoTitle.isNotEmpty()){
            // Create note object
            val todo = Todo(0, todoTitle, todoDesc)
            // Add note to ViewModel
            todoesViewModel.addTodo(todo)

            // Show toast message
            Toast.makeText(addTodoView.context, "Note Saved", Toast.LENGTH_SHORT).show()
            // Navigate back to HomeFragment
            view.findNavController().popBackStack(R.id.homeFragment, false)
        } else {
            Toast.makeText(addTodoView.context, "Please enter note title", Toast.LENGTH_SHORT).show()
        }
    }

    // Inflate the menu layout
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_add_note, menu)
    }

    // Handle menu item click events
    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.saveMenu -> {
                saveTodo(addTodoView)
                true
            }
            else -> false
        }
    }

    // Clear binding when fragment is destroyed to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        addNoteBinding = null
    }
}