package com.example.workoutapp.database.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Exercise(val id: Int,
               val name: String,
               val desc: String,
              val image:Int,
               var isCompleted:Boolean,
               var isOngoing:Boolean,
               val duration: Int):Parcelable
