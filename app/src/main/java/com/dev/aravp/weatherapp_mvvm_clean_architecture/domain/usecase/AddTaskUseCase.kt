package com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.usecase

import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.model.Task
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.repository.TaskRepository

class AddTaskUseCase(private val repository: TaskRepository) {
    suspend fun execute(task: Task) {
        repository.addTask(task)
    }
}