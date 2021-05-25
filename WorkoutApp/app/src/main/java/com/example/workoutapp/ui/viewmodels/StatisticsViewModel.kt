package com.example.workoutapp.ui.viewmodels

import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutapp.R
import com.example.workoutapp.database.Repository
import com.example.workoutapp.database.models.Calories
import com.example.workoutapp.database.models.Workout
import com.example.workoutapp.databinding.WeekViewBinding
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(private val repository: Repository):ViewModel(){
    private val weeklyWorkouts=MutableLiveData<List<Date>>()
    val workouts=weeklyWorkouts as LiveData<*>
    val caloriesBurned=MutableLiveData<List<Calories>>()


    fun getCalories(){
        viewModelScope.launch {
            caloriesBurned.value=repository.caloriesBurned()
        }
    }

    private fun getWeeklyWorkouts(mon:Calendar, sun:Calendar){
        mon.set(Calendar.HOUR,0)
        mon.set(Calendar.MINUTE,0)
        mon.set(Calendar.SECOND,0)
        sun.set(Calendar.HOUR,23)
        sun.set(Calendar.MINUTE,59)
        sun.set(Calendar.SECOND,59)
        viewModelScope.launch {
            weeklyWorkouts.value=repository.getWeeklyWorkouts(mon.time, sun.time)
        }
    }
    fun initializeWeekView(calendarView:WeekViewBinding){
        val day=Calendar.getInstance()
        day.timeInMillis=System.currentTimeMillis()
        val monday=Calendar.getInstance()
        var sunday=Calendar.getInstance()
        if(day.get(Calendar.DAY_OF_WEEK)==1){
            sunday=day
            monday.set(day.get(Calendar.YEAR),day.get(Calendar.MONTH),day.get(Calendar.DAY_OF_MONTH)-6)
        }
        else{
            sunday.set(day.get(Calendar.YEAR),day.get(Calendar.MONTH),day.get(Calendar.DAY_OF_MONTH)+(8-day.get(Calendar.DAY_OF_WEEK)))
            monday.set(day.get(Calendar.YEAR),day.get(Calendar.MONTH),day.get(Calendar.DAY_OF_MONTH)-day.get(Calendar.DAY_OF_WEEK)+2)
        }
        calendarView.mondayTv.text= monday.get(Calendar.DAY_OF_MONTH).toString()
        calendarView.tuesdayTv.text= (monday.get(Calendar.DAY_OF_MONTH)+1).toString()
        calendarView.wednesdayTv.text= (monday.get(Calendar.DAY_OF_MONTH)+2).toString()
        calendarView.thursdayTv.text= (monday.get(Calendar.DAY_OF_MONTH)+3).toString()
        calendarView.fridayTv.text= (monday.get(Calendar.DAY_OF_MONTH)+4).toString()
        calendarView.saturdayTv.text= (monday.get(Calendar.DAY_OF_MONTH)+5).toString()
        calendarView.sundayTv.text= (monday.get(Calendar.DAY_OF_MONTH)+6).toString()
        getWeeklyWorkouts(monday,sunday)
    }
    fun updateWeekView(calendarView: WeekViewBinding,context:Context){
        for(date in weeklyWorkouts.value!!)
        {
            val calendar=Calendar.getInstance()
            calendar.time=date
            val day=calendar.get(Calendar.DAY_OF_WEEK)
            val card=calendarView.root.findViewWithTag(day) as CardView
            card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.secondaryLightColor))
            val textView=card.getChildAt(0) as TextView
            textView.setTextColor(context.getColor(R.color.secondaryDarkColor))
        }
    }

}