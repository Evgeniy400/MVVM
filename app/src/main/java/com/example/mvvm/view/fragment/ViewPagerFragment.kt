package com.example.mvvm.view.fragment

import android.os.Binder
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mvvm.R
import com.example.mvvm.adapter.NotePagerAdapter
import com.example.mvvm.databinding.FragmentViewPagerBinding
import com.example.mvvm.model.database.Note
import java.text.FieldPosition


class ViewPagerFragment(private var notes: List<Note>, private var position: Int) : Fragment() {
    private lateinit var binding: FragmentViewPagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentViewPagerBinding.inflate(layoutInflater)
        binding.viewPager.adapter = NotePagerAdapter(requireActivity()).also {
            it.notes = notes as ArrayList<Note>
        }
        binding.viewPager.setCurrentItem(position, true)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

}