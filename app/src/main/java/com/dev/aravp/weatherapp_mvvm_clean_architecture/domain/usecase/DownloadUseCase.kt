package com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.usecase

import android.content.Context
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.repository.FileRepository
import kotlinx.coroutines.flow.Flow

class DownloadFileUseCase(private val repository: FileRepository) {
    suspend fun execute(url: String, fileName: String, context: Context): Flow<Int> {
        return repository.downloadFile(url, fileName, context)
    }
}
