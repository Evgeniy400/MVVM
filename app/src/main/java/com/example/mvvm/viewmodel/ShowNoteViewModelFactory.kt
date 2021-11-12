package com.example.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.model.Repository
import com.example.mvvm.model.database.Note

class ShowNoteViewModelFactory(private var repository: Repository, private val note: Note) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Repository::class.java, Note::class.java).newInstance(repository, note)
    }
}