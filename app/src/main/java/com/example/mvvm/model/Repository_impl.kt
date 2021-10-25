package com.example.mvvm.model

import com.example.mvvm.model.database.AppDataBase
import com.example.mvvm.model.database.Note

open class Repository_impl(db: AppDataBase): Repository {
    private val dao = db.noteDao()
    override suspend fun addNote(note: Note) {
        dao.addNote(note)
    }

    override suspend fun addNote(title: String, text: String) {
        addNote(Note(title, text))
    }

    override suspend fun getAllNotes() = dao.getAll()

}