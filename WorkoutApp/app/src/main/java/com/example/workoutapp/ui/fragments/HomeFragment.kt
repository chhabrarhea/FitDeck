package com.example.workoutapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.R
import com.example.workoutapp.adapters.RoutineAdapter
import com.example.workoutapp.database.models.Routine
import com.example.workoutapp.database.models.RoutineData
import com.example.workoutapp.databinding.FragmentHomeBinding
import com.example.workoutapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding:FragmentHomeBinding
    private lateinit var adapter: RoutineAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        adapter= RoutineAdapter(RoutineData.routines,requireContext()){item:Routine->routineClickListener((item))}
        binding.recyclerView.adapter=adapter
        binding.recyclerView.layoutManager=LinearLayoutManager(requireContext())
        return binding.root
    }
    private fun routineClickListener(routine: Routine){
        if (routine.id==0)
        {
            findNavController().navigate(R.id.action_homeFragment_to_runTrackFragment)
        }
        else{
        val bundle =Bundle()
        bundle.putParcelable("routine",routine)
        findNavController().navigate(R.id.action_homeFragment_to_routineDetailFragment,bundle)
        }
    }


}