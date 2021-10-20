package com.example.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm.model.MainModel
import com.example.mvvm.model.database.AppDataBase
import com.example.mvvm.model.database.Note

class MainViewModel(dataBase: AppDataBase) : ViewModel() {
    private var _noteCount = MutableLiveData<Int>()
    private var _notes = MutableLiveData<ArrayList<Note>>()
    private var _currentNote = MutableLiveData<Note>()
    private var model = MainModel(dataBase)
    var currentNote: LiveData<Note> = _currentNote
    var noteCount: LiveData<Int> = _noteCount

    suspend fun initVM() {
        _notes.value = ArrayList(model.getAllNotes())
        _noteCount.value = _notes.value?.size
    }

    suspend fun addNote(title: String, text: String) {
        if (title != "null" && text != "null") {
            model.addNote(title, text)
            _notes.value?.add(Note(title, text))
            _noteCount.value = _noteCount.value?.inc()
        }
    }

    fun pageSelected(position: Int) {
        _currentNote.value = _notes.value?.get(position)
    }
}