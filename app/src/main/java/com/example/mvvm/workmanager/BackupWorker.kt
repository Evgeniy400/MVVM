package com.example.mvvm.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.mvvm.model.BackupRepository
import com.example.mvvm.model.BackupRepositoryImpl
import com.example.mvvm.model.database.AppDataBase

class BackupWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {
    private val repository: BackupRepository =
        BackupRepositoryImpl(AppDataBase.getDatabase(context))

    override suspend fun doWork(): Result {
        repository.getNotes().forEach {
            Log.d("CoroutineWorker", it.toString())
        }
        return Result.success()
    }
}