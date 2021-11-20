package com.example.mvvm.model

import androidx.lifecycle.LiveData
import com.example.mvvm.model.database.Note

interface Repository {
    suspend fun addNote(note: Note)

    suspend fun addNote(title: String, text: String)

    fun getAllNotes(): LiveData<List<Note>>



    suspend fun deleteNote(note: Note)
}