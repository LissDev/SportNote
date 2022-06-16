package ru.lissdev.sportnote.data.network.wger

import io.reactivex.rxjava3.core.Single
import ru.lissdev.sportnote.BuildConfig
import ru.lissdev.sportnote.data.model.ExerciseDetails
import ru.lissdev.sportnote.data.model.ExercisesCategoriesPage
import ru.lissdev.sportnote.data.model.ExercisesListPage
import ru.lissdev.sportnote.data.network.core.Client

object WgerClient {

    const val LANG_ID = 2 // eng

    private val client by lazy {
        Client(
            BuildConfig.API_BASE_URL,
            "Authorization",
            BuildConfig.API_KEY,
            WgerApi::class
        )
    }

    private val api: WgerApi by lazy { client.api }

    fun getExercisesCategories(): Single<ExercisesCategoriesPage> = api.getExerciseCategories()
    fun getExercisesByCategories(categoryId: Long): Single<ExercisesListPage> = api.getExercisesByCategories(
        mapOf(
            "category" to "$categoryId",
            "language" to "$LANG_ID"
        )
    )
    fun getExercise(exerciseId: Long): Single<ExerciseDetails> = api.getExercise(exerciseId)
}
