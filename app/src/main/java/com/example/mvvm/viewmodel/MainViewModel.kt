package com.example.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mvvm.model.Repository

class MainViewModel(private var repository: Repository) : ViewModel() {
    fun getAllNotes() = repository.getAllNotes()
}