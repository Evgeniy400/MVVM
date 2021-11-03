package com.example.mvvm.view

import android.content.Intent
import android.icu.text.DateFormat.MEDIUM
import android.icu.text.DateFormat.getDateInstance
import android.os.Bundle
import android.text.format.DateFormat.getMediumDateFormat
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.mvvm.R
import com.example.mvvm.adapter.NotePagerAdapter
import com.example.mvvm.databinding.ActivityMainBinding
import com.example.mvvm.model.Repository_impl
import com.example.mvvm.model.database.AppDataBase
import com.example.mvvm.view.fragment.AboutDialogFragment
import com.example.mvvm.viewmodel.MainViewModel
import com.example.mvvm.viewmodel.MyViewModelFactory
//import java.text.DateFormat
import java.util.*
import android.text.format.DateFormat
import com.example.mvvm.model.RepositoryImpl
import com.example.mvvm.model.database.Note
import java.text.DateFormat.MEDIUM
import java.text.DateFormat.getDateInstance


class MainActivity : FragmentActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: NotePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, MyViewModelFactory(RepositoryImpl(AppDataBase.getDatabase(this)))).get(
            MainViewModel::class.java
        )
        viewModel.initVM()

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

        observe()
    }

    private fun observe(){
        viewModel.noteCount.observe(this) {
            adapter.size = it
        }

        viewModel.onSuccessSaveNote.observe(this) {
            Toast.makeText(
                this,
                getString(R.string.success_save_toast),
                Toast.LENGTH_LONG
            ).show()
        }

        viewModel.onErrorSaveNote.observe(this) {
            Toast.makeText(
                this,
                getString(R.string.error_save_toast),
                Toast.LENGTH_LONG
            ).show()
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
        val title = data?.getStringExtra("Title").toString()
        val text = data?.getStringExtra("Text").toString()
        val date = (getMediumDateFormat(this)).format(Date())
        viewModel.addNote(title, text, date)

    }
}