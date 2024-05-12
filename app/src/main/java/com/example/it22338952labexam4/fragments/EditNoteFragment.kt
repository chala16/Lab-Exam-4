package com.example.it22338952labexam4.fragments

import android.app.AlertDialog
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
import androidx.navigation.fragment.navArgs
import com.example.it22338952labexam4.MainActivity
import com.example.it22338952labexam4.R
import com.example.it22338952labexam4.databinding.FragmentEditNoteBinding
import com.example.it22338952labexam4.model.Todo
import com.example.it22338952labexam4.viewmodel.TodoViewModel

// Fragment for editing an existing note
class EditNoteFragment : Fragment(R.layout.fragment_edit_note), MenuProvider {


    private var editNoteBinding: FragmentEditNoteBinding? = null
    private val binding get() = editNoteBinding!!

    private lateinit var todoesViewModel: TodoViewModel
    private lateinit var currentTodo: Todo

    private val args: EditNoteFragmentArgs by navArgs()

    // Inflate the layout for this fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        editNoteBinding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up menu provider
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // Initialize TodoViewModel
        todoesViewModel = (activity as MainActivity).todoViewModel
        // Get current Todo from navigation arguments
        currentTodo = args.todo!!

        // Populate EditText fields with current Todo data
        binding.editTodoTitle.setText(currentTodo.todoTitle)
        binding.editTodoDesc.setText(currentTodo.todoDesc)

        // Set click listener for the save button
        binding.editTodoFab.setOnClickListener{
            val todoTitle = binding.editTodoTitle.text.toString().trim()
            val todoDesc = binding.editTodoDesc.text.toString().trim()

            if (todoTitle.isNotEmpty()){
                // Create updated Todo object
                val todo = Todo(currentTodo.id, todoTitle, todoDesc)
                // Update Todo in ViewModel
                todoesViewModel.updateTodo(todo)
                // Navigate back to HomeFragment
                view.findNavController().popBackStack(R.id.homeFragment, false)
            } else {
                // Show error message if title is empty
                Toast.makeText(context, "Please enternote title", Toast.LENGTH_SHORT).show()

            }
        }
    }

    // Function to delete the current Todo
    private fun deleteTodo(){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Do you want to delete this note")
            setPositiveButton("Delete"){_,_ ->
                // Delete Todo from ViewModel
                todoesViewModel.deleteTodo(currentTodo)
                // Show toast message
                Toast.makeText(context, "Note Deleted!", Toast.LENGTH_SHORT).show()
                // Navigate back to HomeFragment
                view?.findNavController()?.popBackStack(R.id.homeFragment, false)
            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }

    // Inflate the menu layout
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_note, menu)
    }

    // Handle menu item click events
    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu -> {
                deleteTodo()
                true
            } else -> false
        }
    }

    // Clear binding when fragment is destroyed to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        editNoteBinding = null
    }
}