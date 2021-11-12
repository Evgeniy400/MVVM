package com.example.mvvm.model.database

import androidx.lifecycle.LiveData
import androidx.room.*
import java.text.FieldPosition

@Dao
interface NoteDAO {
    @Query("SELECT * FROM notes")
    fun getAll(): LiveData<List<Note>>

    @Insert
    suspend fun addNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

}