package com.dvilson.todoapp.ui.tasks

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dvilson.todoapp.R
import com.dvilson.todoapp.databinding.FragmentTasksBinding
import com.dvilson.todoapp.util.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskFragment : Fragment(R.layout.fragment_tasks) {

    private val viewModel: TaskViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        val binding = FragmentTasksBinding.bind(view)

        val taskAdapter = TaskAdapter()

        binding.apply {
            recycleview.apply {
                adapter = taskAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        viewModel.tasks.observe(viewLifecycleOwner) {
            taskAdapter.submitList(it)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_tasks, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searcheView = searchItem.actionView as SearchView
        searcheView.onQueryTextChanged {
            viewModel.serchQuery.value= it
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.action_sort_by_name -> {
                true
            }
            R.id.action_sort_by_date_created -> {
                true

            }
            R.id.action_hide_is_completed -> {
                item.isChecked = !item.isChecked
                true
            }
            R.id.action_delete_all_completed -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}