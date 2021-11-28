package com.example.mvvm.view.fragment

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mvvm.adapter.RecyclerViewAdapter
import com.example.mvvm.databinding.FragmentRecyclerBinding

class RecyclerViewFragment(private var adapter: RecyclerViewAdapter) : Fragment() {
    private lateinit var binding: FragmentRecyclerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentRecyclerBinding.inflate(layoutInflater)
        binding.recyclerView.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
}