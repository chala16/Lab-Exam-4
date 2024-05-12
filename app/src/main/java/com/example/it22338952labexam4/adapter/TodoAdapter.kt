package com.example.it22338952labexam4.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.it22338952labexam4.databinding.NoteLayoutBinding
import com.example.it22338952labexam4.fragments.HomeFragmentDirections
import com.example.it22338952labexam4.model.Todo

// Adapter class for the RecyclerView
class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    // ViewHolder class that holds references to views for each item in the RecyclerView
    class TodoViewHolder(val itemBinding: NoteLayoutBinding): RecyclerView.ViewHolder(itemBinding.root)

    // Callback for calculating the difference between two lists of Todos
    private val differCallback = object : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            // Check if the unique identifiers of the items are the same
            return oldItem.id == newItem.id &&
                    oldItem.todoDesc == newItem.todoTitle &&
                    oldItem.todoTitle == newItem.todoTitle
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            // Check if the contents of the items are the same
            return oldItem == newItem
        }
    }
    // List differ that computes the difference between two lists on a background thread
    val differ = AsyncListDiffer(this, differCallback)
    // Create new ViewHolders
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        // Inflate the layout for each item in the RecyclerView
        return TodoViewHolder(
            NoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false )
        )
    }

    // Return the total number of items in the list
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    // Bind data to the views in each ViewHolder
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        // Get the note object at the current position
        val currentTodo = differ.currentList[position]

        // Bind the data to the views in the ViewHolder
        holder.itemBinding.TodoTitle.text = currentTodo.todoTitle
        holder.itemBinding.TodoDesc.text = currentTodo.todoDesc

        // Set click listener to navigate to EditNoteFragment with the selected note
        holder.itemView.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToEditNoteFragment(currentTodo)
            it.findNavController().navigate(direction)
        }
    }
}