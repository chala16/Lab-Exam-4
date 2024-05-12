package com.example.it22338952labexam4.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.it22338952labexam4.MainActivity
import com.example.it22338952labexam4.R
import com.example.it22338952labexam4.adapter.TodoAdapter
import com.example.it22338952labexam4.databinding.FragmentHomeBinding
import com.example.it22338952labexam4.model.Todo
import com.example.it22338952labexam4.viewmodel.TodoViewModel

// Fragment for displaying the list of notes
class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener, MenuProvider {

    private var homeBinding: FragmentHomeBinding? = null
    private val binding get() = homeBinding!!

    private lateinit var todoesViewModel: TodoViewModel
    private lateinit var todoAdapter: TodoAdapter

    // Inflate the layout for this fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set up menu provider
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        // Initialize TodoViewModel
        todoesViewModel = (activity as MainActivity).todoViewModel
        setupHomeRecyclerView()
        // Navigate to AddNoteFragment when FAB is clicked
        binding.addNoteFab.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)

        }
    }

    // Update UI based on the list of todos
    private fun updateUI(todo: List<Todo>?){
        if (todo != null){
            if(todo.isNotEmpty()){
                binding.emptyNotesImage.visibility = View.GONE
                binding.homeRecyclerView.visibility = View.VISIBLE
            } else {
                binding.emptyNotesImage.visibility = View.VISIBLE
                binding.homeRecyclerView.visibility = View.VISIBLE
            }
        }
    }

    // Set up RecyclerView to display todos
    private fun setupHomeRecyclerView(){
        todoAdapter = TodoAdapter()
        binding.homeRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = todoAdapter
        }

        activity?.let {
            // Observe changes in the list of todos
            todoesViewModel.getAllTodoes().observe(viewLifecycleOwner){ todo ->
                // Update the adapter with the new list of todos
                todoAdapter.differ.submitList(todo)
                // Update UI based on the list of todos
                updateUI(todo)
            }
        }
    }

    // Search todos based on query text
    private fun searchTodo(query: String?){
        val searchQuery = "%$query"

        todoesViewModel.searchTodo(searchQuery).observe(this) {list ->
            todoAdapter.differ.submitList(list)
        }
    }
    // Handle submit query event
    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }
    // Handle text change event
    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null){
            searchTodo(newText)
        }
        return true
    }
    // Clear binding when fragment is destroyed to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        homeBinding = null
    }
    // Inflate the menu layout and set up SearchView
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.home_menu, menu)

        val menuSearch = menu.findItem(R.id.searchMenu).actionView as SearchView
        menuSearch.isSubmitButtonEnabled = false
        menuSearch.setOnQueryTextListener(this)
    }
    // Handle menu item click events
    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }
}