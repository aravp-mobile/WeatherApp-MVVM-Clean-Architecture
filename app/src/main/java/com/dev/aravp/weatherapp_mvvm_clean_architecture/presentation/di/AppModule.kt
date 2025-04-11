package com.dev.aravp.weatherapp_mvvm_clean_architecture.presentation.di

import android.content.Context
import androidx.room.Room
import com.dev.aravp.weatherapp_mvvm_clean_architecture.data.local.dao.TaskDao
import com.dev.aravp.weatherapp_mvvm_clean_architecture.data.local.database.TaskDatabase
import com.dev.aravp.weatherapp_mvvm_clean_architecture.data.repository.TaskRepositoryImpl
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.repository.TaskRepository
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.usecase.AddTaskUseCase
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.usecase.GetTaskUseCase
import com.dev.aravp.weatherapp_mvvm_clean_architecture.presentation.ui.task.TaskViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(
        @ApplicationContext context: Context
    ): TaskDatabase {
        return Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            "task_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(taskDatabase: TaskDatabase): TaskDao {
        return taskDatabase.taskDao()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository {
        return TaskRepositoryImpl(taskDao)
    }

    @Provides
    @Singleton
    fun provideGetTasksUseCase(taskRepository: TaskRepository): GetTaskUseCase {
        return GetTaskUseCase(taskRepository)
    }

    @Provides
    @Singleton
    fun provideAddTaskUseCase(taskRepository: TaskRepository): AddTaskUseCase {
        return AddTaskUseCase(taskRepository)
    }

    @Provides
    @Singleton
    fun provideTaskViewModel(
        getTasksUseCase: GetTaskUseCase,
        addTaskUseCase: AddTaskUseCase
    ): TaskViewModel {
        return TaskViewModel(getTasksUseCase, addTaskUseCase)
    }
}