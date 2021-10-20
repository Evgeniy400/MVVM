package com.example.mvvm.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.R
import com.example.mvvm.databinding.FragmentShowNoteBinding
import com.example.mvvm.model.database.AppDataBase
import com.example.mvvm.viewmodel.MainViewModel
import com.example.mvvm.viewmodel.MyViewModelFactory

class ShowNoteFragment : Fragment() {
    private lateinit var binding: FragmentShowNoteBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentShowNoteBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(
            requireActivity(),
            MyViewModelFactory(AppDataBase.getDatabase(requireActivity()))
        ).get(MainViewModel::class.java)
        viewModel.currentNote.observe(this) {
            binding.noteTitleFragment.text = it.title
            binding.noteDateFragment.text = it.time
            binding.noteTextFragment.text = it.text
        }

        binding.shareNote.setOnClickListener {
            startActivity(Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_TEXT,
                    "${viewModel.currentNote.value?.title}\n${viewModel.currentNote.value?.text}"
                )
            })
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

}