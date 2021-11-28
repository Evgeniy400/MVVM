package com.example.mvvm.model

import com.example.mvvm.model.database.AppDataBase
import com.example.mvvm.model.database.Note


class BackupRepositoryImpl(db: AppDataBase): BackupRepository {
    private val dao = db.noteDao()

    override suspend fun getNotes(): List<Note> =  emptyList()//dao.getAllSuspend()
}