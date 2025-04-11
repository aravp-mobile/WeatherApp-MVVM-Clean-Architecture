package com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.usecase

import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.model.Task
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetTaskUseCase(private val repository: TaskRepository) {
    suspend fun execute(): Flow<List<Task>> = repository.getTasks()
}