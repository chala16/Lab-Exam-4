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

class EditNoteFragment : Fragment(R.layout.fragment_edit_note), MenuProvider {


    private var editNoteBinding: FragmentEditNoteBinding? = null
    private val binding get() = editNoteBinding!!

    private lateinit var todoesViewModel: TodoViewModel
    private lateinit var currentTodo: Todo

    private val args: EditNoteFragmentArgs by navArgs()
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

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        todoesViewModel = (activity as MainActivity).todoViewModel
        currentTodo = args.todo!!

        binding.editTodoTitle.setText(currentTodo.todoTitle)
        binding.editTodoDesc.setText(currentTodo.todoDesc)

        binding.editTodoFab.setOnClickListener{
            val todoTitle = binding.editTodoTitle.text.toString().trim()
            val todoDesc = binding.editTodoDesc.text.toString().trim()

            if (todoTitle.isNotEmpty()){
                val todo = Todo(currentTodo.id, todoTitle, todoDesc)
                todoesViewModel.updateTodo(todo)
                view.findNavController().popBackStack(R.id.homeFragment, false)
            } else {
                Toast.makeText(context, "Please enternote title", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun deleteTodo(){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Do you want to delete this note")
            setPositiveButton("Delete"){_,_ ->
                todoesViewModel.deleteTodo(currentTodo)
                Toast.makeText(context, "Note ", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment, false)
            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_note, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu -> {
                deleteTodo()
                true
            } else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editNoteBinding = null
    }
}