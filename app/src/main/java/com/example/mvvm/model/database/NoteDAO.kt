package com.example.mvvm.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDAO {
    @Query("SELECT * FROM notes")
    suspend fun getAll(): List<Note>


    @Query("SELECT * FROM notes")
    fun getProfiles(): LiveData<List<Note>>

    @Query("Select * from notes where id == :index")
    suspend fun findNoteById(index: Long): Note

    @Insert
    suspend fun addNote(note: Note)

}