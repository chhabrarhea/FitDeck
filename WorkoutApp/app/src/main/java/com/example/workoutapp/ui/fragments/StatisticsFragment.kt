package com.example.workoutapp.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.workoutapp.R
import com.example.workoutapp.databinding.FragmentStatisticsBinding
import com.example.workoutapp.other.CustomAxisFormatter
import com.example.workoutapp.ui.viewmodels.StatisticsViewModel
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class StatisticsFragment : Fragment() {
    private val viewModel: StatisticsViewModel by viewModels()
    val data = ArrayList<BarEntry>()
    private lateinit var  binding:FragmentStatisticsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View{

        binding= FragmentStatisticsBinding.inflate(layoutInflater, null, false)
        subscribeToObservers()
        viewModel.getCalories()
        viewModel.initializeWeekView(binding.weekView)

        return binding.root
    }

    private fun subscribeToObservers() {
        viewModel.workouts.observe(viewLifecycleOwner, {
            viewModel.updateWeekView(binding.weekView, requireContext())
        })
        viewModel.caloriesBurned.observe(viewLifecycleOwner, {
            initializeGraph()
            val allEntries = it.indices.map { i -> BarEntry(i.toFloat(), it[i].calories.toFloat()) }
            val dataSet = BarDataSet(allEntries, "Estimated Calories").apply {
                valueTextColor = Color.BLACK
            }
            binding.calorieChart.data = BarData(dataSet)
            dataSet.setGradientColor(ContextCompat.getColor(requireContext(), R.color.secondaryLightColor),ContextCompat.getColor(requireContext(), R.color.secondaryDarkColor))
            binding.calorieChart.invalidate()

        })
    }
    private fun initializeGraph(){
        binding.calorieChart.setDrawBarShadow(false)
        binding.calorieChart.setDrawValueAboveBar(true)
        binding.calorieChart.description.isEnabled = false
        binding.calorieChart.isHovered=false
        binding.calorieChart.legend.isEnabled=false

        val xAxisFormatter: IndexAxisValueFormatter = CustomAxisFormatter(viewModel.caloriesBurned.value!!)

        val xAxis: XAxis = binding.calorieChart.xAxis
        xAxis.position = XAxisPosition.BOTTOM

        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.valueFormatter = xAxisFormatter


        val rightAxis: YAxis = binding.calorieChart.axisRight
        rightAxis.setDrawGridLines(false)
        rightAxis.spaceTop = 15f
        rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        rightAxis.axisMaximum=500f
        rightAxis.labelCount=5


        val leftAxis: YAxis = binding.calorieChart.axisLeft
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.spaceTop = 15f
        leftAxis.axisMaximum=500f
        leftAxis.axisMinimum = 0f
        leftAxis.labelCount=5
        // this replaces setStartAtZero(true)




    }




}





