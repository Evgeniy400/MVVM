package com.example.mvvm.view

import android.os.Bundle
import android.text.format.DateFormat
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.R
import com.example.mvvm.databinding.ActivityAddNoteBinding
import com.example.mvvm.model.RepositoryImpl
import com.example.mvvm.model.database.AppDataBase
import com.example.mvvm.viewmodel.AddNoteViewModel
import com.example.mvvm.viewmodel.MyViewModelFactory
import java.util.*

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var viewModel: AddNoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(RepositoryImpl(AppDataBase.getDatabase(this)))
        ).get(
            AddNoteViewModel::class.java
        )

        binding.button.setOnClickListener {
            viewModel.addNote(
                binding.editTextTitle.text.toString(),
                binding.editTextText.text.toString(),
                (DateFormat.getMediumDateFormat(this)).format(Date())
            )
        }

        binding.buttonLoad.setOnClickListener {
            viewModel.loadNote()
        }

        observeToViewModel()
    }

    private fun observeToViewModel() {
        viewModel.onSuccessSaveNote.observe(this) {
            Toast.makeText(
                this,
                getString(R.string.success_save_toast),
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }

        viewModel.onErrorSaveNote.observe(this) {
            Toast.makeText(
                this,
                getString(R.string.error_save_toast),
                Toast.LENGTH_SHORT
            ).show()
        }

        viewModel.onLoadNoteFailed.observe(this) {
            Toast.makeText(
                this,
                getString(R.string.error_download),
                Toast.LENGTH_SHORT
            ).show()
        }

        viewModel.loadNote.observe(this) {
            binding.editTextTitle.setText(it.title)
            binding.editTextText.setText(it.body)
        }
    }


}