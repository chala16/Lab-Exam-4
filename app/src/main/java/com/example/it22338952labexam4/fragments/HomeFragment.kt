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

class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener, MenuProvider {

    private var homeBinding: FragmentHomeBinding? = null
    private val binding get() = homeBinding!!

    private lateinit var todoesViewModel: TodoViewModel
    private lateinit var todoAdapter: TodoAdapter

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

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        todoesViewModel = (activity as MainActivity).todoViewModel
        setupHomeRecyclerView()

        binding.addNoteFab.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)

        }
    }

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

    private fun setupHomeRecyclerView(){
        todoAdapter = TodoAdapter()
        binding.homeRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = todoAdapter
        }

        activity?.let {
            todoesViewModel.getAllTodoes().observe(viewLifecycleOwner){ todo ->
                todoAdapter.differ.submitList(todo)
                updateUI(todo)
            }
        }
    }

    private fun searchTodo(query: String?){
        val searchQuery = "%$query"

        todoesViewModel.searchTodo(searchQuery).observe(this) {list ->
            todoAdapter.differ.submitList(list)
        }
    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null){
            searchTodo(newText)
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        homeBinding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.home_menu, menu)

        val menuSearch = menu.findItem(R.id.searchMenu).actionView as SearchView
        menuSearch.isSubmitButtonEnabled = false
        menuSearch.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }
}