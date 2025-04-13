package com.dev.aravp.weatherapp_mvvm_clean_architecture.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.model.FileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFile(file: FileEntity)

    @Query("SELECT * FROM downloaded_files")
    fun getAllFiles(): Flow<List<FileEntity>>
}
