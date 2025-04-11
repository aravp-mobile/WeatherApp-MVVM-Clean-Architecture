package com.dev.aravp.weatherapp_mvvm_clean_architecture.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM tasks")
    fun getActiveTasks(): Flow<List<Task>>
}
