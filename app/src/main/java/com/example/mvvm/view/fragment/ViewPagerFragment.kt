package com.example.mvvm.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.adapter.NotePagerAdapter
import com.example.mvvm.databinding.FragmentViewPagerBinding
import com.example.mvvm.model.RepositoryImpl
import com.example.mvvm.model.database.AppDataBase
import com.example.mvvm.viewmodel.MainViewModel
import com.example.mvvm.viewmodel.MyViewModelFactory


class ViewPagerFragment : Fragment() {
    private lateinit var binding: FragmentViewPagerBinding
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            requireActivity(),
            MyViewModelFactory(RepositoryImpl(AppDataBase.getDatabase(requireContext())))
        ).get(MainViewModel::class.java)
        binding = FragmentViewPagerBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeVM()
    }

    private fun subscribeVM() {
        viewModel.searchResult.observe(viewLifecycleOwner) {
            binding.viewPager.adapter = NotePagerAdapter(requireActivity()).apply {
                notes = it
            }
            binding.viewPager.setCurrentItem(arguments?.getInt(POSITION) ?: 0, true)
        }
    }

    companion object {
        val POSITION = "position"
    }
}