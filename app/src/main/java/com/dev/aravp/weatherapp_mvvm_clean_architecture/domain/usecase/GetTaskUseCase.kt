package com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.usecase

import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.model.Task
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

/**
 * What is UseCase ?
 * A UseCase is a class that represents a single action or piece of business logic in your app.
 * UseCases are used to :
 *  1) Encapsulate business logic - Keeps operations separated from the UI or logic
 *  2) Improve testability - You can unit test them independently without worrying about ViewModel or Repository details
 *  3) Keeps ViewModel clean - ViewModel shouldn’t care how adding or fetching happens — it just calls the UseCase.
 *  4) Follow Single Responsibility Principle (SRP) - Each UseCase should have a single responsibility
 *  5) Enable reusability - You can reuse UseCases across different parts of your app (UI, background tasks, etc).
 */
class GetTaskUseCase(private val repository: TaskRepository) {
    suspend fun execute(): Flow<List<Task>> = repository.getTasks()
}