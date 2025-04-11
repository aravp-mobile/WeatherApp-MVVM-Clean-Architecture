package com.dev.aravp.weatherapp_mvvm_clean_architecture.data.repository

import com.dev.aravp.weatherapp_mvvm_clean_architecture.data.local.dao.TaskDao
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.model.Task
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {

    override suspend fun getTasks(): Flow<List<Task>> = taskDao.getActiveTasks()

    override suspend fun addTask(task: Task) {
        taskDao.insertTask(task)
    }

    override suspend fun updateTask(task: Task) {
        taskDao.update(task)
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.delete(task)
    }

}