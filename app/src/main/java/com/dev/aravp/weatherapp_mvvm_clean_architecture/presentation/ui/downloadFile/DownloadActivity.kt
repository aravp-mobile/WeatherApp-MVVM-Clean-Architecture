package com.dev.aravp.weatherapp_mvvm_clean_architecture.presentation.ui.downloadFile

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dev.aravp.weatherapp_mvvm_clean_architecture.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DownloadActivity : AppCompatActivity() {

    private val viewModel: DownloadViewModel by viewModels()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)

        val button = findViewById<Button>(R.id.btnDownload)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        button.setOnClickListener {
            viewModel.downloadFile(
                url = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf",
                fileName = "Dummy_pdf_url.pdf",
                context = this
            )
        }

        lifecycleScope.launch {
            viewModel.downloadProgress.collect { progress ->
                progressBar.progress = progress
            }
        }
    }
}
