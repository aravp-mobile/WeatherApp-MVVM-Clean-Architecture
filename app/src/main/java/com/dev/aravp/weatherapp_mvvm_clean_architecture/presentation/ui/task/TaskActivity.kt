package com.dev.aravp.weatherapp_mvvm_clean_architecture.presentation.ui.task

import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.aravp.weatherapp_mvvm_clean_architecture.R
import com.dev.aravp.weatherapp_mvvm_clean_architecture.databinding.ActivityTaskBinding
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.model.Task
import com.dev.aravp.weatherapp_mvvm_clean_architecture.presentation.ui.base.BaseActivity
import com.dev.aravp.weatherapp_mvvm_clean_architecture.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskActivity : BaseActivity<ActivityTaskBinding>(), View.OnClickListener {

    private lateinit var taskAdapter: TaskAdapter
    private val taskViewModel: TaskViewModel by viewModels()

    override fun getLayoutRes(): Int =
        R.layout.activity_task

    override fun initViews() {
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        taskAdapter = TaskAdapter()
        binding.rcvTasks.apply {
            layoutManager = LinearLayoutManager(this@TaskActivity)
            adapter = taskAdapter
        }
    }

    override fun initListeners() {
        binding.apply {
            btnAddTask.setOnClickListener(this@TaskActivity)
        }
    }

    override fun setObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                taskViewModel.tasks.collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            // Show loading
                        }
                        is Result.Success -> {
                            taskAdapter.submitList(result.data)
                        }
                        is Result.Error -> {
                            // Show error message
                        }
                    }
                }
            }
        }
    }

    override fun setViewBinding(): ActivityTaskBinding {
        return ActivityTaskBinding.inflate(this.layoutInflater)
    }

    override fun onClick(v: View?) {
        binding.apply {
            when (v) {
                btnAddTask -> {
                    val taskName = etTask.text.toString().trim()
                    if (taskName.isNotEmpty()) {
                        taskViewModel.addTask(Task(name = taskName))
                        etTask.text?.clear()
                    }
                }
            }
        }
    }
}