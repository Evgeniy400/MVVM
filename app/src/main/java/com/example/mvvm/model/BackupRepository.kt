package com.example.mvvm.model

import com.example.mvvm.model.database.Note

interface BackupRepository {
    suspend fun getNotes(): List<Note>
}