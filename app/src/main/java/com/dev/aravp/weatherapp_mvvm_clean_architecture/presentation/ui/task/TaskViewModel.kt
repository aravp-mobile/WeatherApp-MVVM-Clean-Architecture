package com.dev.aravp.weatherapp_mvvm_clean_architecture.presentation.ui.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.model.Task
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.usecase.AddTaskUseCase
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.usecase.GetTaskUseCase
import com.dev.aravp.weatherapp_mvvm_clean_architecture.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This ViewModel is clean, reactive implementation that follows modern Android best practices:
 * 🟦 StateFlow for reactive data updates
 * ⚡ Kotlin Coroutines
 * ✅ Hilt for Dependency Injection
 * 📦 Result wrapper for handling loading/success/error states
 * 📚 Clean Architecture and Separation of Concerns
 */


/**
 * @HiltViewModel  tells Hilt to manage the lifecycle and dependencies of this ViewModel.
 * @Inject constructor() Hilt automatically injects the required dependencies:
 *          GetTaskUseCase
 *          AddTaskUseCase
 */
@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTaskUseCase: GetTaskUseCase,
    private val addTaskUseCase: AddTaskUseCase
) : ViewModel() {

    /**
     * MutableStateFlow - Its a reactive stream of data that can be collected from the UI
     * _tasks is private so only ViewModel can update it.
     * tasks is exposed as StateFlow to the UI (read-only)
     */
    private val _tasks = MutableStateFlow<Result<List<Task>>>(Result.Loading)
    val tasks: StateFlow<Result<List<Task>>> = _tasks

    /**
     * Called when the ViewModel is initialized.
     * Automatically starts observing the task list from the database
     */
    init {
        observeTasks()
    }

    private fun observeTasks() {
        viewModelScope.launch {
            getTaskUseCase.execute()
                // Sets loading state before the flow starts emitting
                .onStart {
                    _tasks.value = Result.loading()
                }
                // Handles any error and emits Result.error
                .catch { e ->
                    _tasks.value = Result.error(e)
                }
                // Collect the flow and emits Result.success
                .collect { taskList ->
                    _tasks.value = Result.success(taskList)
                }
        }
    }

    /**
     * When user adds a task, this method is triggered.
     * Calls the AddTaskUseCase, which saves to the DB.
     */
    fun addTask(task: Task) {
        // Launches in background (viewModelScope)
        viewModelScope.launch {
            try {
                addTaskUseCase.execute(task)
                // No need to manually refresh — Flow handles updates automatically
                // Because Room emits new data via Flow, which your ViewModel is already observing
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
