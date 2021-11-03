package com.example.mvvm.model

import com.example.mvvm.model.database.Note
import com.example.mvvm.network.NoteModel

interface Repository {
    suspend fun addNote(note: Note)

    suspend fun addNote(title: String, text: String)

    suspend fun getAllNotes(): List<Note>

    suspend fun downloadNote(): NoteModel
}