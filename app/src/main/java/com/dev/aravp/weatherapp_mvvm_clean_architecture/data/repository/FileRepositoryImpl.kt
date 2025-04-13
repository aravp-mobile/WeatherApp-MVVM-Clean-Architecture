package com.dev.aravp.weatherapp_mvvm_clean_architecture.data.repository

import android.content.Context
import com.dev.aravp.weatherapp_mvvm_clean_architecture.data.local.dao.FileDao
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.model.DownloadStatus
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.model.FileEntity
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.repository.FileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class FileRepositoryImpl(private val dao: FileDao) : FileRepository {

    override fun downloadFile(url: String, fileName: String, context: Context): Flow<Int> = flow {
        emit(0) // Initial progress

        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.connect()

            val fileLength = connection.contentLength
            val input = BufferedInputStream(connection.inputStream)
            val file = File(context.getExternalFilesDir(null), fileName)
            val output = FileOutputStream(file)

            val buffer = ByteArray(1024)
            var total = 0
            var count: Int

            while (input.read(buffer).also { count = it } != -1) {
                total += count
                output.write(buffer, 0, count)
                emit((total * 100) / fileLength)
            }

            output.flush()
            output.close()
            input.close()

            dao.insertFile(
                FileEntity(
                    fileName = fileName,
                    filePath = file.absolutePath,
                    fileSize = file.length(),
                    fileType = file.extension,
                    status = DownloadStatus.COMPLETED
                )
            )

        } catch (e: Exception) {
            dao.insertFile(
                FileEntity(
                    fileName = fileName,
                    filePath = "",
                    fileSize = 0,
                    fileType = "unknown",
                    status = DownloadStatus.FAILED
                )
            )
            emit(-1)
        }
    }.flowOn(Dispatchers.IO)
}
