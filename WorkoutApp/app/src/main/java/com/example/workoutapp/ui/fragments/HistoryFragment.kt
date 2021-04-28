package com.example.workoutapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.R
import com.example.workoutapp.adapters.HistoryAdapter
import com.example.workoutapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_history.*

@AndroidEntryPoint
class HistoryFragment : Fragment() {
   private val viewModel:MainViewModel by viewModels()
   private lateinit var runAdapter:HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =inflater.inflate(R.layout.fragment_history, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.runsSortedByDate.observe(viewLifecycleOwner, {
            runAdapter.submitList(it)
        })
    }

    private fun setupRecyclerView() = rvRuns?.apply {
        runAdapter = HistoryAdapter()
        adapter = runAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }


}