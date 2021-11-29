package com.example.mvvm.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm.model.Repository
import com.example.mvvm.model.database.Note

class MainViewModel(private var repository: Repository) : ViewModel() {
    private var _searchResult = MutableLiveData<List<Note>>()
    val searchResult: LiveData<List<Note>> = _searchResult
    fun getAllNotes() = repository.getAllNotes()

    var notes = emptyList<Note>()

    fun search(query: String?) {
        if (!query.isNullOrEmpty()) {
            notes.filter {
                it.title.contains(other = query, true) ||
                        it.time.contains(other = query, true)
            }.also {
                _searchResult.value = it
            }
        } else {
            _searchResult.value = notes
        }
    }
}