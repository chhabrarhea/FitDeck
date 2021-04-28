package com.example.workoutapp.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.R
import com.example.workoutapp.adapters.ExerciseAdapter
import com.example.workoutapp.database.models.Exercise
import com.example.workoutapp.database.models.Routine
import com.example.workoutapp.databinding.ExerciseDetailDialogBinding
import com.example.workoutapp.databinding.FragmentRoutineDetailBinding
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class RoutineDetailFragment : Fragment(),AppBarLayout.OnOffsetChangedListener {
   private lateinit var binding:FragmentRoutineDetailBinding
   private lateinit var routine:Routine
    private lateinit var adapter:ExerciseAdapter
    private var isShow = true
    private var scrollRange = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding= FragmentRoutineDetailBinding.inflate(layoutInflater,null,false)
        binding.appbar.addOnOffsetChangedListener(this)
        binding.startBtn.setOnClickListener {
            val bundle=Bundle()
            bundle.putParcelable("routine",routine)
            findNavController().navigate(R.id.action_routineDetailFragment_to_restFragment,bundle)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        routine= arguments?.getParcelable("routine")!!
        binding.routine=routine
        adapter= ExerciseAdapter(requireContext(),routine.exercises){exercise:Exercise->exerciseClickListener(exercise)}
        binding.recyclerView.adapter=adapter
        binding.recyclerView.layoutManager=LinearLayoutManager(requireContext())

    }

    private fun exerciseClickListener(exercise: Exercise){
        val alertDialogBuilder=MaterialAlertDialogBuilder(requireContext())
        val alertDialogBinding=ExerciseDetailDialogBinding.inflate(layoutInflater,null,false)
        alertDialogBuilder.setView(alertDialogBinding.root)
        alertDialogBinding.gif.setImageResource(exercise.image)
        alertDialogBinding.exercise=exercise
        val alertDialog=alertDialogBuilder.create()
        alertDialogBinding.closeButton.setOnClickListener { alertDialog.dismiss() }
        alertDialog.show()
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (scrollRange == -1){
            scrollRange = appBarLayout?.totalScrollRange!!
        }
        if (scrollRange + verticalOffset == 0){
            binding.collapseToolbar.title = routine.name
            binding.toolbar.setBackgroundColor(Color.parseColor(routine.color))
            binding.toolbar.fitsSystemWindows=true
            binding.collapseToolbar.setStatusBarScrimColor(Color.parseColor(routine.color))
            isShow = true
        } else if (isShow){
            binding.toolbar.setBackgroundColor(Color.TRANSPARENT)
            binding.collapseToolbar.title = " " //careful there should a space between double quote otherwise it wont work
            isShow = false
            binding.toolbar.fitsSystemWindows=false
        }
    }




}