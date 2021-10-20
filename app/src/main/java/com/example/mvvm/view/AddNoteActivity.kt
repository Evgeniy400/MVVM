package com.example.mvvm.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.mvvm.R
import com.example.mvvm.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val intent = Intent()
            intent.putExtra("Title", findViewById<EditText>(R.id.editTextTitle).text.toString())
            intent.putExtra("Text", findViewById<EditText>(R.id.editTextText).text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}