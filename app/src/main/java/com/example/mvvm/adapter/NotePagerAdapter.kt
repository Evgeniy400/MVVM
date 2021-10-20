package com.example.mvvm.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mvvm.view.fragment.ShowNoteFragment

class NotePagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    var size = 0
    override fun getItemCount(): Int = size

    override fun createFragment(position: Int): Fragment {
        return ShowNoteFragment()
    }
}