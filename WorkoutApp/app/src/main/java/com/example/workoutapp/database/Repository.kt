package com.example.workoutapp.database


import com.example.workoutapp.database.models.Workout
import javax.inject.Inject

class Repository @Inject constructor(private val dao:WorkoutDAO) {


    suspend fun insertWorkout(workout: Workout){
        dao.insertRun(workout)
    }
    suspend fun deleteWorkout(workout: Workout)=dao.deleteRun(workout)

    fun getAllWorkoutsSortedByDate() = dao.getAllRunsSortedByDate()

    fun getAllRunsSortedByDistance() = dao.getAllRunsSortedByDistance()

    fun getAllWorkoutsSortedByTimeInMillis() = dao.getAllRunsSortedByTimeInMillis()

    fun getAllRunsSortedByAvgSpeed() = dao.getAllRunsSortedByAvgSpeed()

    fun getAllWorkoutsSortedByCaloriesBurned() = dao.getAllRunsSortedByCaloriesBurned()

//    fun getAllWorkoutsSortedByCategory()=

    fun getTotalAvgSpeed() = dao.getTotalAvgSpeed()

    fun getTotalDistance() = dao.getTotalDistance()

    fun getTotalCaloriesBurned() = dao.getTotalCaloriesBurned()

    fun getTotalTimeInMillis() = dao.getTotalTimeInMillis()
}