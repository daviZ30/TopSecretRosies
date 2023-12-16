package com.moronlu18.task.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import com.moronlu18.task.entity.Task
import com.moronlu18.task.repository.ProviderTask
import com.moronlu18.task.usecase.TaskViewModel
import com.moronlu18.taskFragment.R
import com.moronlu18.taskFragment.databinding.FragmentTaskDetailBinding

class TaskDetailFragment : Fragment() {
    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskViewModel by viewModels()

    private lateinit var tasks : MutableList<Task>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tasks = viewModel.tasksList
        parentFragmentManager.setFragmentResultListener("key", this,
            FragmentResultListener { _, result ->
                var pos: Int = result.getInt("position")
                val task = tasks[pos]
                binding.tvTaskDetailTitle.text = task.title
                binding.tvTaskDetailClienteCont.text = task.nameCustomer
                binding.tvTaskDetailDescCont.text = task.description
                binding.tvTaskDetailDateStartCont.text = task.createdDate
                binding.tvTaskDetailDateEndCont.text = task.endDate
                binding.tvTaskDetailTypeCont.text = task.type.toString()
                binding.tvTaskDetailStateCont.text = task.state.toString()
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}