package com.example.mvvm.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mvvm.model.database.Note
import com.example.mvvm.view.fragment.ShowNoteFragment

class NotePagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {

    var notes = emptyList<Note>()

    override fun getItemCount(): Int = notes.size

    override fun createFragment(position: Int): Fragment {
        val frag = ShowNoteFragment()
        frag.arguments = Bundle().apply {
            putParcelable(ShowNoteFragment.NOTE, notes[position])
        }
        return frag
    }
}