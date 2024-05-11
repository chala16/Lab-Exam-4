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

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(val itemBinding: NoteLayoutBinding): RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.todoDesc == newItem.todoTitle &&
                    oldItem.todoTitle == newItem.todoTitle
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            NoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentTodo = differ.currentList[position]

        holder.itemBinding.TodoTitle.text = currentTodo.todoTitle
        holder.itemBinding.TodoDesc.text = currentTodo.todoDesc

        holder.itemView.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToEditNoteFragment(currentTodo)
            it.findNavController().navigate(direction)
        }
    }
}