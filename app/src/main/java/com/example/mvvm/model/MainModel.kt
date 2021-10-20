package com.example.mvvm.model

import com.example.mvvm.model.database.AppDataBase
import com.example.mvvm.model.database.Note

class MainModel(var db: AppDataBase) {
    private suspend fun addNote(note: Note) {
        db.noteDao().addNote(note)
    }

    suspend fun addNote(title: String, text: String) {
        addNote(Note(title, text))
    }

    suspend fun getAllNotes() = db.noteDao().getAll()

}