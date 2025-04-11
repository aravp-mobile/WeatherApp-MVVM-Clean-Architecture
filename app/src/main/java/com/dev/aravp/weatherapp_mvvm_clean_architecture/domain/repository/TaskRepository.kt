package com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.repository

import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun getTasks(): Flow<List<Task>>
    suspend fun addTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
}