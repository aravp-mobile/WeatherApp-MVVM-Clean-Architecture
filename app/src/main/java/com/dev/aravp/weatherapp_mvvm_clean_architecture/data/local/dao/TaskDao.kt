package com.dev.aravp.weatherapp_mvvm_clean_architecture.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.model.Task
import kotlinx.coroutines.flow.Flow

/**
 * DAO - Data Access Object
 * It's a Room annotation based interface that defines how to interact with the database-  like.
 * 1) Insert new rows
 * 2) Query data
 * 3) Update existing rows
 * 4) Delete rows
 *
 * Room auto-generates the implementation of this interface at compile-time.
 */
@Dao
interface TaskDao {

    /**
     * Why suspend keyword here ?
     * Access the disk, which is slow and shouldn't block the Main/UI thread.
     * Coroutines (suspend) allow them to run in the background (IO Dispatcher)
     */
    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM tasks")
    /**
     * Return the result as Kotlin Flow - this is reactive
     * No suspend is needed because -
     *  1) Flow is already asynchronous and lazy
     *  2) Room will handle the background thread
     */
    fun getActiveTasks(): Flow<List<Task>>
}
