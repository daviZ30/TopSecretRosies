package com.moronlu18.task.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.moronlu18.invoice.MainActivity
import com.moronlu18.task.adapter.TaskListAdapter
import com.moronlu18.task.entity.Task
import com.moronlu18.task.repository.ProviderTask
import com.moronlu18.task.usecase.TaskViewModel
import com.moronlu18.taskFragment.R
import com.moronlu18.taskFragment.databinding.FragmentTaskListBinding

class TaskListFragment : Fragment(), MenuProvider {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskViewModel by viewModels()

    private lateinit var taskListAdapter : TaskListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
         taskListAdapter = TaskListAdapter ({ pos:Int, nav:Int ->
            var bundle = Bundle()
            bundle.putInt("position",pos)
            parentFragmentManager.setFragmentResult("key",bundle)
            if(nav == 0){
                findNavController().navigate(R.id.action_taskListFragment_to_taskDetailFragment)
            } else if(nav == 1){
                findNavController().navigate(R.id.action_taskListFragment_to_taskCreationFragment)
            }
        },{ task : Task ->
            viewModel.deleteTask(task)
             binding.rvTaskList.adapter?.notifyDataSetChanged()
         })
        binding.rvTaskList.adapter = taskListAdapter
        binding.rvTaskList.scrollToPosition(ProviderTask.taskExample.size - 1)
        binding.rvTaskList.layoutManager = LinearLayoutManager(context)
        setUpToolbar()
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.allTasks.observe(viewLifecycleOwner){tasks ->
            taskListAdapter.submitList(tasks)
        }

        binding.fabTaskList.setOnClickListener {
            findNavController().navigate(R.id.action_taskListFragment_to_taskCreationFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_list_task, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.refreshTask -> {
                viewModel.sortId()
                binding.rvTaskList.adapter?.notifyDataSetChanged()
                return true
            }
            R.id.sortTask -> {
                viewModel.sortCustomer()
                binding.rvTaskList.adapter?.notifyDataSetChanged()
                return true
            }else -> false
        }
    }

    private fun setUpToolbar() {
        //Modismo aplly de kotlin
        (requireActivity() as? MainActivity)?.toolbar?.apply {
            visibility = View.VISIBLE
        }
        val menuhost: MenuHost = requireActivity()
        menuhost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}

