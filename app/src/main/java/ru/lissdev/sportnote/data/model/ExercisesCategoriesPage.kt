package ru.lissdev.sportnote.data.model

data class ExercisesCategoriesPage(
    val count: Int = 0,
    val results: List<ExercisesCategory> = emptyList()
)