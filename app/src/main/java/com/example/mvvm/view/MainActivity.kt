package com.example.mvvm.view

import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat.getMediumDateFormat
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.R
import com.example.mvvm.adapter.NotePagerAdapter
import com.example.mvvm.databinding.ActivityMainBinding
import com.example.mvvm.model.database.AppDataBase
import com.example.mvvm.view.fragment.AboutDialogFragment
import com.example.mvvm.viewmodel.MainViewModel
import com.example.mvvm.viewmodel.MyViewModelFactory
//import java.text.DateFormat
import java.util.*
import com.example.mvvm.model.RepositoryImpl
import com.example.mvvm.model.database.Note
import kotlin.collections.ArrayList


class MainActivity : FragmentActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: NotePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(RepositoryImpl(AppDataBase.getDatabase(this)))
        ).get(
            MainViewModel::class.java
        )

        adapter = NotePagerAdapter(this)
        binding.viewPager.adapter = adapter

        binding.imageButtonAbout.setOnClickListener {
            AboutDialogFragment().show(supportFragmentManager, null)
        }

        binding.imageButtonAdd.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }

        observeToViewModel()
    }

    private fun observeToViewModel() {
        viewModel.getAllNotes().observe(this) {
            var ind = binding.viewPager.currentItem
            adapter = NotePagerAdapter(this)
            adapter.notes = it as ArrayList<Note>
            binding.viewPager.adapter = adapter
            binding.viewPager.currentItem = if (ind == adapter.itemCount) ind - 1 else ind
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (binding.viewPager.currentItem == 0)
            super.onBackPressed()
        else
            binding.viewPager.currentItem -= 1
    }

}