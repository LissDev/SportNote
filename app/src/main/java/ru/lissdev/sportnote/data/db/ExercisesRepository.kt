package ru.lissdev.sportnote.data.db

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import ru.lissdev.sportnote.data.model.ExerciseDetails

interface ExercisesRepository {
    fun insertExercise(exercise: ExerciseDetails): Completable
    fun getExercises(): Maybe<List<ExerciseDetails>>
}