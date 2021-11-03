package com.example.mvvm.model

import com.example.mvvm.model.database.AppDataBase
import com.example.mvvm.model.database.Note
import com.example.mvvm.network.NoteInteractor
import com.example.mvvm.network.NoteModel
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

open class RepositoryImpl(db: AppDataBase) : Repository {
    private val dao = db.noteDao()
    override suspend fun addNote(note: Note) {
        dao.addNote(note)
    }

    override suspend fun addNote(title: String, text: String) {
        addNote(Note(title, text))
    }

    override suspend fun getAllNotes() = dao.getAll()

    override suspend fun downloadNote(): NoteModel {
        var note = NoteModel("", "")
        NoteInteractor().getNote().enqueue(object : retrofit2.Callback<NoteModel>{
            override fun onResponse(call: Call<NoteModel>, response: Response<NoteModel>) {
                note = response.body() ?: NoteModel("", "")
            }

            override fun onFailure(call: Call<NoteModel>, t: Throwable) {
                note = NoteModel("", "")
            }

        })
        return note
    }



}