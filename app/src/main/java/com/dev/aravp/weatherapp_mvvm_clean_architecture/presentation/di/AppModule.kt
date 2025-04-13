package com.dev.aravp.weatherapp_mvvm_clean_architecture.presentation.di

import android.content.Context
import androidx.room.Room
import androidx.work.impl.Migration_1_2
import com.dev.aravp.weatherapp_mvvm_clean_architecture.data.local.dao.FileDao
import com.dev.aravp.weatherapp_mvvm_clean_architecture.data.local.dao.TaskDao
import com.dev.aravp.weatherapp_mvvm_clean_architecture.data.local.database.TaskDatabase
import com.dev.aravp.weatherapp_mvvm_clean_architecture.data.repository.FileRepositoryImpl
import com.dev.aravp.weatherapp_mvvm_clean_architecture.data.repository.TaskRepositoryImpl
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.repository.FileRepository
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.repository.TaskRepository
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.usecase.AddTaskUseCase
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.usecase.DownloadFileUseCase
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.usecase.GetTaskUseCase
import com.dev.aravp.weatherapp_mvvm_clean_architecture.presentation.ui.task.TaskViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Module - Tells Hilt this object provides dependencies
 * @InstallIn(SingletonComponent::class): Says all the dependencies here will live for the
 * entire app's lifecycle (singleton scope).
 *
 * Think of this module like a factory that knows how to create and give you instances of
 * classes you need — like your database, DAO, repository, use cases, etc.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provides a single instance of the Room database.
     * Uses @ApplicationContext so it doesn't hold an activity reference.
     * Singleton — you only need one database instance in your app.
     */
    @Provides
    @Singleton
    fun provideTaskDatabase(
        @ApplicationContext context: Context
    ): TaskDatabase {
        return Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            "task_db"
        ).addMigrations(Migration_1_2)
            .build()
    }

    /**
     * Gets the DAO from the database instance
     * Room generates this method for you (taskDatabase.taskDao())
     */
    @Provides
    @Singleton
    fun provideTaskDao(taskDatabase: TaskDatabase): TaskDao {
        return taskDatabase.taskDao()
    }

    /**
     * Binding interface @TaskRepository to its implementation @TaskRepositoryImpl.
     *
     */
    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository {
        return TaskRepositoryImpl(taskDao)
    }

    /**
     * Each UseCase gets the repository injected
     * These encapsulates the business logic (Clean Architecture)
     * Keeps ViewModel clean and logic-focused
     */
    @Provides
    @Singleton
    fun provideGetTasksUseCase(taskRepository: TaskRepository): GetTaskUseCase {
        return GetTaskUseCase(taskRepository)
    }

    /**
     * Each UseCase gets the repository injected
     */
    @Provides
    @Singleton
    fun provideAddTaskUseCase(taskRepository: TaskRepository): AddTaskUseCase {
        return AddTaskUseCase(taskRepository)
    }

    @Provides
    @Singleton
    fun provideFileDao(taskDatabase: TaskDatabase): FileDao {
        return taskDatabase.fileDao()
    }

    @Provides
    fun provideDownloadUseCase(repo: FileRepository): DownloadFileUseCase {
        return DownloadFileUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideDownloadRepository(fileDao: FileDao) : FileRepository {
        return FileRepositoryImpl(fileDao)
    }
}