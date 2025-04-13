package com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "downloaded_files")
data class FileEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fileName: String,
    val filePath: String,
    val fileType: String,
    val fileSize: Long,
    val status: DownloadStatus
)
