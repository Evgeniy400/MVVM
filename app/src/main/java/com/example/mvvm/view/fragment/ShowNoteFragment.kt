package com.example.mvvm.view.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.databinding.FragmentShowNoteBinding
import com.example.mvvm.model.RepositoryImpl
import com.example.mvvm.model.database.AppDataBase
import com.example.mvvm.model.database.Note
import com.example.mvvm.viewmodel.MyViewModelFactory
import com.example.mvvm.viewmodel.ShowNoteViewModel
import com.example.mvvm.viewmodel.ShowNoteViewModelFactory

class ShowNoteFragment() : Fragment() {
    private lateinit var binding: FragmentShowNoteBinding
    private lateinit var viewModel: ShowNoteViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentShowNoteBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(
            this,
            ShowNoteViewModelFactory(
                RepositoryImpl(AppDataBase.getDatabase(requireActivity())),
                arguments?.getParcelable("note") ?: Note("", "")
            )
        ).get(ShowNoteViewModel::class.java)

        viewModel.noteData.observe(this){

        }

        binding.shareNote.setOnClickListener {
            startActivity(Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_TEXT,
                    "${viewModel.noteData.value?.title}\n${viewModel.noteData.value?.text}"
                )
            })
        }

        binding.deleteNote.setOnClickListener {
            viewModel.deleteNote()
        }
        viewModel.noteData.value?.let {
            binding.noteTitleFragment.text = "${it.title} #${it.id}"
            binding.noteDateFragment.text = it.time
            binding.noteTextFragment.text = it.text
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
}