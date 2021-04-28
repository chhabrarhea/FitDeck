package com.example.workoutapp.di

import android.content.Context
import androidx.room.Room
import com.example.workoutapp.database.WorkoutDatabase
import com.example.workoutapp.other.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.DefineComponent
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {
    @Singleton
    @Provides
    fun provideWorkoutDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        WorkoutDatabase::class.java,
        Constants.WORKOUT_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideWorkoutDao(db: WorkoutDatabase) = db.getRunDao()
}