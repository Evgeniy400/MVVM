package com.example.mvvm.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.example.mvvm.adapter.NotePagerAdapter
import com.example.mvvm.adapter.RecyclerViewAdapter
import com.example.mvvm.databinding.ActivityMainBinding
import com.example.mvvm.model.database.AppDataBase
import com.example.mvvm.view.fragment.AboutDialogFragment
import com.example.mvvm.viewmodel.MainViewModel
import com.example.mvvm.viewmodel.MyViewModelFactory
import com.example.mvvm.model.RepositoryImpl
import com.example.mvvm.model.database.Note
import com.example.mvvm.view.fragment.RecyclerViewFragment
import com.example.mvvm.view.fragment.ViewPagerFragment
import com.example.mvvm.workmanager.BackupWorker
import java.util.concurrent.TimeUnit.*
import kotlin.collections.ArrayList


open class MainActivity : FragmentActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

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

        initWorkers()

        supportFragmentManager.beginTransaction().replace(
            binding.fragmentContainerView.id, RecyclerViewFragment()
        ).commit()
    }

    private fun initWorkers() {
        WorkManager.getInstance().enqueue(
            PeriodicWorkRequest.Builder(
                BackupWorker::class.java, 1, MINUTES
            )
                .build()
        )
    }
}