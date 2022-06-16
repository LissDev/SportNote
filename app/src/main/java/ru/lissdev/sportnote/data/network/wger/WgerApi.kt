package ru.lissdev.sportnote.data.network.wger

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.lissdev.sportnote.data.model.ExerciseDetails
import ru.lissdev.sportnote.data.model.ExercisesCategoriesPage
import ru.lissdev.sportnote.data.model.ExercisesListPage

interface WgerApi {
    @GET("/api/v2/exercisecategory")
    fun getExerciseCategories() : Single<ExercisesCategoriesPage>;

    @GET("/api/v2/exercise/")
    fun getExercisesByCategories(@QueryMap query: Map<String, String>) : Single<ExercisesListPage>;

    @GET("/api/v2/exerciseinfo/{id}")
    fun getExercise(@Path("id") id: Long) : Single<ExerciseDetails>;
}