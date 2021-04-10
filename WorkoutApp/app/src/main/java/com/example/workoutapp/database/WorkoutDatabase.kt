package com.example.workoutapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Workout::class],version = 1)
@TypeConverters(Converter::class)
abstract  class Database : RoomDatabase() {

        abstract fun getRunDao(): WorkoutDAO
    }
