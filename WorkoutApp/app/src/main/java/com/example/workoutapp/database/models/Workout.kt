package com.example.workoutapp.database

import android.graphics.Bitmap
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_table")
data class Workout(
    //0->run, 1->obliques, 2->fat burning, 3->legs
    @NonNull var category:Int,
    var img: Bitmap? = null,
    var timestamp: Long = 0L,
    var avgSpeedInKMH: Float = 0f,
    var distanceInMeters: Int = 0,
    var timeInMillis: Long = 0L,
    var caloriesBurned: Int = 0
){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}