package com.dev.aravp.weatherapp_mvvm_clean_architecture.presentation.ui.downloadFile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.usecase.DownloadFileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DownloadViewModel @Inject constructor(
    private val useCase: DownloadFileUseCase
) : ViewModel() {

    private val _downloadProgress = MutableStateFlow(0)
    val downloadProgress: StateFlow<Int> = _downloadProgress

    fun downloadFile(url: String, fileName: String, context: Context) {
        viewModelScope.launch {
            useCase.execute(url, fileName, context).collect {
                _downloadProgress.value = it
            }
        }
    }
}
