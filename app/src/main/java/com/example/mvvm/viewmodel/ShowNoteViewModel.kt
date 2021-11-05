package com.example.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.model.Repository
import com.example.mvvm.model.database.Note
import kotlinx.coroutines.launch

class ShowNoteViewModel(private var repository: Repository): ViewModel() {
    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }
}