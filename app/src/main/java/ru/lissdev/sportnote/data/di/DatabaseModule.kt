package ru.lissdev.sportnote.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.lissdev.sportnote.data.db.AppDatabase
import ru.lissdev.sportnote.data.db.ExercisesDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideExercisesDao(appDatabase: AppDatabase): ExercisesDao {
        return appDatabase.exercisesDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.createDatabaseInstance(appContext)
    }
}