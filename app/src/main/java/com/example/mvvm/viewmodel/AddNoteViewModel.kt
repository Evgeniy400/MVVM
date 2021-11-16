package com.example.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.google.SingleLiveEvent
import com.example.mvvm.model.Repository
import com.example.mvvm.model.database.Note
import com.example.mvvm.network.NetworkModel
import com.example.mvvm.network.NoteInteractor
import com.example.mvvm.network.NoteInteractorImp
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddNoteViewModel(private var repository: Repository) : ViewModel() {
    private val _loadNote = MutableLiveData<NetworkModel>()
    private val noteInteractor: NoteInteractor = NoteInteractorImp()

    val onSuccessSaveNote = SingleLiveEvent<Unit>()
    val onErrorSaveNote = SingleLiveEvent<Unit>()
    val onLoadNoteFailed = SingleLiveEvent<Unit>()
    val loadNote: LiveData<NetworkModel> = _loadNote


    fun loadNote() {
        noteInteractor.getData(
            success = { model: NetworkModel -> _loadNote.value = model},
            failure = { onLoadNoteFailed.call()})
    }

    fun addNote(title: String, text: String, date: String) {
        if (title != "" && text != "") {
            viewModelScope.launch {
                repository.addNote(Note(title, text, date))
            }
            onSuccessSaveNote.call()
        } else {
            onErrorSaveNote.call()
        }
    }
}