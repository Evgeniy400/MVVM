package com.example.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.google.SingleLiveEvent
import com.example.mvvm.model.Repository
import com.example.mvvm.model.database.Note
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel(private var repository: Repository) : ViewModel() {
    private var _noteCount = MutableLiveData<Int>()
    private var _notes = MutableLiveData<ArrayList<Note>>()
    private var _currentNote = MutableLiveData<Note>()
    var currentNote: LiveData<Note> = _currentNote
    var noteCount: LiveData<Int> = _noteCount


    val onSuccessSaveNote = SingleLiveEvent<Unit>()
    val onErrorSaveNote = SingleLiveEvent<Unit>()

    fun initVM() {
        viewModelScope.launch {
            _notes.value = ArrayList(repository.getAllNotes())
            _noteCount.value = _notes.value?.size
        }
    }

    fun addNote(title: String?, text: String?, date: String) {

        if (title != null && text != null && title != "" && text != "") {
            viewModelScope.launch {
                repository.addNote(Note(title, text, date.toString()))
            }

            _notes.value?.add(Note(title, text, date.toString()))
            _noteCount.value = _noteCount.value?.inc()
            onSuccessSaveNote.call()
        } else {
            onErrorSaveNote.call()
        }

    }

    fun pageSelected(position: Int) {
        _currentNote.value = _notes.value?.get(position)
    }
}