package ru.lissdev.sportnote.data.model

import kotlin.math.ceil

class ExercisesListPage(
    val results: List<Exercise> = emptyList(),
    val count: Int = 0,
    val offset: Int = 0
) {

    val isFirstPage: Boolean
        get() = offset == 0

    val pagesCount: Int
        get() = ceil(count.toFloat() / results.size).toInt()
}