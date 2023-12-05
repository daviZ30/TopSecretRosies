package com.moronlu18.task.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.moronlu18.taskFragment.R
import com.moronlu18.task.adapter.TaskListAdapter
import com.moronlu18.task.entity.Task
import com.moronlu18.task.repository.ProviderTask
import com.moronlu18.taskFragment.databinding.FragmentTaskListBinding

class TaskListFragment : Fragment() {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private val tasks : MutableList<Task> =  ProviderTask.taskExample

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)/*
        parentFragmentManager.setFragmentResultListener("key", this,
            FragmentResultListener { _, result ->
                var pos: Int = result.getInt("pos")
                binding.rvTaskList.adapter = TaskListAdapter(tasks) { _, _ ->
                    var bundle = Bundle()
                    bundle.putInt("position",pos)
                    parentFragmentManager.setFragmentResult("key",bundle)
                }
                binding.rvTaskList.layoutManager = LinearLayoutManager(context)
            })*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        val adapter = TaskListAdapter(tasks) { pos:Int, nav:Int ->
            var bundle = Bundle()
            bundle.putInt("position",pos)
            parentFragmentManager.setFragmentResult("key",bundle)
            if(nav == 0){
                findNavController().navigate(R.id.action_taskListFragment_to_taskDetailFragment)
            } else if(nav == 1){
                findNavController().navigate(R.id.action_taskListFragment_to_taskCreationFragment)
            }
        }
        binding.rvTaskList.adapter = adapter
        binding.rvTaskList.scrollToPosition(tasks.size - 1)
        binding.rvTaskList.layoutManager = LinearLayoutManager(context)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabTaskList.setOnClickListener {
            findNavController().navigate(R.id.action_taskListFragment_to_taskCreationFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

