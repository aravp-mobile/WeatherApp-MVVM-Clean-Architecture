package com.dev.aravp.weatherapp_mvvm_clean_architecture.presentation.ui.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.model.Task
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.usecase.AddTaskUseCase
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.usecase.GetTaskUseCase
import com.dev.aravp.weatherapp_mvvm_clean_architecture.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTaskUseCase: GetTaskUseCase,
    private val addTaskUseCase: AddTaskUseCase
) : ViewModel() {

    private val _tasks = MutableStateFlow<Result<List<Task>>>(Result.Loading)
    val tasks: StateFlow<Result<List<Task>>> = _tasks

    init {
        observeTasks()
    }

    private fun observeTasks() {
        viewModelScope.launch {
            getTaskUseCase.execute()
                .onStart {
                    _tasks.value = Result.Loading
                }
                .catch { e ->
                    _tasks.value = Result.Error(e)
                }
                .collect { taskList ->
                    _tasks.value = Result.Success(taskList)
                }
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            try {
                addTaskUseCase.execute(task)
                // No need to manually refresh — Flow handles updates automatically
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
