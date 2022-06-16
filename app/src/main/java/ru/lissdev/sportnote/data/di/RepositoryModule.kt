package ru.lissdev.sportnote.data.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.lissdev.sportnote.data.db.AppDatabase
import ru.lissdev.sportnote.data.db.ExercisesDao
import ru.lissdev.sportnote.data.db.ExercisesRepository
import ru.lissdev.sportnote.data.db.ExercisesRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesExercisesRepository(impl: ExercisesRepositoryImpl): ExercisesRepository
}