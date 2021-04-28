package com.example.workoutapp.ui.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutapp.database.Repository
import com.example.workoutapp.database.models.Workout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel@Inject constructor(
    private val repository: Repository
): ViewModel() {
    val runsSortedByDate=repository.getAllWorkoutsSortedByDate()
    fun insertWorkout(workout: Workout){
        viewModelScope.launch {
            repository.insertWorkout(workout)
        }
    }
}