package com.example.workoutapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.workoutapp.database.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(val repository: Repository):ViewModel(){
}