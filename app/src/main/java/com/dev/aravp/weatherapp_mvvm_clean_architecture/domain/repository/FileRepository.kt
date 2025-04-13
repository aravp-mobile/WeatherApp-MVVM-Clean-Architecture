package com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow

interface FileRepository {
    fun downloadFile(url: String, fileName: String, context: Context): Flow<Int>
}