package com.example.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.model.Repository
import com.example.mvvm.model.database.Note
import kotlinx.coroutines.launch

class ShowNoteViewModel(private var repository: Repository, private val note: Note): ViewModel() {
    fun deleteNote() {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    val noteData: LiveData<Note> = MutableLiveData(note)
}