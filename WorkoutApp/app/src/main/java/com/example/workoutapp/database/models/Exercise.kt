package com.example.workoutapp.database.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Exercise(
               val name: String,
               val desc: String,
              val image:String,
               val duration: Int,
               val reps:Int,
               val isGif:Boolean
):Parcelable
