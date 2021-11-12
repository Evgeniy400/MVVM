package com.example.mvvm.network

interface NoteInteractor {
    fun getData(success: (NetworkModel) -> Unit, failure: () -> Unit)
}