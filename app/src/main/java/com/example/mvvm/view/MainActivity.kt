package com.example.mvvm.view

import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat.getMediumDateFormat
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.mvvm.R
import com.example.mvvm.adapter.NotePagerAdapter
import com.example.mvvm.databinding.ActivityMainBinding
import com.example.mvvm.model.database.AppDataBase
import com.example.mvvm.view.fragment.AboutDialogFragment
import com.example.mvvm.viewmodel.MainViewModel
import com.example.mvvm.viewmodel.MyViewModelFactory
import java.util.*
import com.example.mvvm.model.RepositoryImpl
import com.example.mvvm.model.database.Note
import com.example.mvvm.workmanager.BackupWorker
import java.util.concurrent.TimeUnit.*
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

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.search(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.search(newText)
                return true
            }

        })

        observeToViewModel()
        initWorkers()
    }


    // когда я присваиваю адаптеру новые данные и вызываю
    // adapter.notifyDataSetChanged() то вывод на экран не корректный
    // пересоздание адаптера наверное не правильный способ, но так работает правильно
    private fun observeToViewModel() {
        viewModel.getAllNotes().observe(this) {
            viewModel.notes = it
            val ind = binding.viewPager.currentItem
            adapter = NotePagerAdapter(this)
            adapter.notes = it as ArrayList<Note>

            binding.viewPager.adapter = adapter
            binding.viewPager.currentItem = if (ind == adapter.itemCount) ind - 1 else ind
        }

        viewModel.searchResult.observe(this) {
            adapter = NotePagerAdapter(this)
            adapter.notes = it as ArrayList<Note>
            binding.viewPager.adapter = adapter
        }
    }

    private fun initWorkers() {
        WorkManager.getInstance().enqueue(
            PeriodicWorkRequest.Builder(
                BackupWorker::class.java, 20, MINUTES
            ).build()
        )
    }


    override fun onBackPressed() {
        super.onBackPressed()
        if (binding.viewPager.currentItem == 0)
            super.onBackPressed()
        else
            binding.viewPager.currentItem -= 1
    }


}