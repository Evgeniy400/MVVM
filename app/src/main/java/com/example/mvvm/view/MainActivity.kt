package com.example.mvvm.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.mvvm.adapter.NotePagerAdapter
import com.example.mvvm.databinding.ActivityMainBinding
import com.example.mvvm.model.database.AppDataBase
import com.example.mvvm.view.fragment.AboutDialogFragment
import com.example.mvvm.viewmodel.MainViewModel
import com.example.mvvm.viewmodel.MyViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : FragmentActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: NotePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, MyViewModelFactory(AppDataBase.getDatabase(this))).get(
            MainViewModel::class.java
        )
        lifecycleScope.launch {
            viewModel.initVM()
        }

        adapter = NotePagerAdapter(this)
        binding.viewPager.adapter = adapter

        binding.imageButtonAbout.setOnClickListener {
            AboutDialogFragment().show(supportFragmentManager, null)
        }

        binding.imageButtonAdd.setOnClickListener {
            startActivityForResult(Intent(this, AddNoteActivity::class.java), 1)
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.pageSelected(position)
            }
        })

        viewModel.noteCount.observe(this) {
            adapter.size = it
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (binding.viewPager.currentItem == 0)
            super.onBackPressed()
        else
            binding.viewPager.currentItem -= 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        lifecycleScope.launch {
            val title = data?.getStringExtra("Title").toString()
            val text = data?.getStringExtra("Text").toString()
            viewModel.addNote(title, text)
        }
    }
}