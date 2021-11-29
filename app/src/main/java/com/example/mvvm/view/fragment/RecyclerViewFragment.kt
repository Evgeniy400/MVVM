package com.example.mvvm.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.R
import com.example.mvvm.adapter.RecyclerViewAdapter
import com.example.mvvm.databinding.FragmentRecyclerBinding
import com.example.mvvm.model.RepositoryImpl
import com.example.mvvm.model.database.AppDataBase
import com.example.mvvm.viewmodel.MainViewModel
import com.example.mvvm.viewmodel.MyViewModelFactory

class RecyclerViewFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            requireActivity(),
            MyViewModelFactory(RepositoryImpl(AppDataBase.getDatabase(requireContext())))
        ).get(MainViewModel::class.java)
        binding = FragmentRecyclerBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeVM()
    }

    private fun subscribeVM() {
        viewModel.searchResult.observe(viewLifecycleOwner) { list ->
            val adapter = RecyclerViewAdapter(list) { pos ->
                val fragment = ViewPagerFragment()
                fragment.arguments = Bundle().apply {
                    putInt(ViewPagerFragment.POSITION, pos)
                }
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragmentContainerView, fragment)
                    ?.addToBackStack(null)?.commit()
            }
            binding.recyclerView.adapter = adapter
        }
    }
}


