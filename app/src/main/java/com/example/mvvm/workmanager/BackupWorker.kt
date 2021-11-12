package com.example.mvvm.workmanager

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.mvvm.R

class BackupWorker(private val context: Context, workerParameters: WorkerParameters): Worker(context, workerParameters) {
    override fun doWork(): Result {
        Log.d("BackupWorker", "do some work")
        return Result.success()
    }

}