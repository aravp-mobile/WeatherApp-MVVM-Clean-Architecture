package com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.model

data class DownloadedFile(
    val id: Int = 0,
    val fileName: String,
    val filePath: String,
    val fileType: String,
    val fileSize: Long,
    val status: DownloadStatus
)
