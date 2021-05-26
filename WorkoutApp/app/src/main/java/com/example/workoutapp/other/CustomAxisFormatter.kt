package com.example.workoutapp.other

import android.util.Log
import com.example.workoutapp.database.models.Calories
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class CustomAxisFormatter(val calories:List<Calories>):IndexAxisValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        val date=calories[value.toInt()].relativeDate
        val dateFormat=SimpleDateFormat("dd MMM ''yy", Locale.getDefault())
        return dateFormat.format(date)
    }



}