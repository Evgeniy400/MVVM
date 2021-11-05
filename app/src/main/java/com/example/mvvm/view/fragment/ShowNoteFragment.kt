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

class ShowNoteFragment(private var note: Note) : Fragment() {
    private lateinit var binding: FragmentShowNoteBinding
    private lateinit var viewModel: ShowNoteViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentShowNoteBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(
            requireActivity(),
            MyViewModelFactory(RepositoryImpl(AppDataBase.getDatabase(requireActivity())))
        ).get(ShowNoteViewModel::class.java)

        binding.shareNote.setOnClickListener {
            startActivity(Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_TEXT,
                    "${note.title}\n${note.text}"
                )
            })
        }

        binding.deleteNote.setOnClickListener{
            viewModel.deleteNote(note)
        }

        binding.noteTitleFragment.text = "${note.title} #${note.id}"
        binding.noteDateFragment.text = note.time
        binding.noteTextFragment.text = note.text
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
}