package ru.lissdev.sportnote.data.db

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.lissdev.sportnote.data.model.ExerciseDetails
import ru.lissdev.sportnote.data.model.ExerciseEquipment
import ru.lissdev.sportnote.data.model.ExerciseMuscle
import ru.lissdev.sportnote.data.model.ExercisesCategory
import javax.inject.Inject

class ExercisesRepositoryImpl @Inject constructor(
    private val exercisesDao: ExercisesDao
) : ExercisesRepository {
    override fun insertExercise(exercise: ExerciseDetails): Completable {
        return exercisesDao.insertCategory(exercise.category)
            .subscribeOn(Schedulers.io())
            .andThen(
                exercisesDao.insertExercise(exercise.apply { category_id = category.id })
                    .subscribeOn(Schedulers.io())
                    .andThen(
                        if (exercise.equipment != null)
                            exercisesDao.insertEquipment(
                                exercise.equipment!!.toMutableList().apply {
                                    val iterate = listIterator()
                                    while (iterate.hasNext()) {
                                        val oldVal = iterate.next().apply {
                                            exercise_id = exercise.id
                                        }
                                        iterate.set(oldVal)
                                    }
                                })
                                .subscribeOn(Schedulers.io())
                        else
                            Completable.complete()
                    )
                    .andThen(
                        if (exercise.muscles != null)
                            exercisesDao.insertMuscles(exercise.muscles!!.toMutableList().apply {
                                val iterate = listIterator()
                                while (iterate.hasNext()) {
                                    val oldVal = iterate.next().apply {
                                        isSecondary = false
                                        exercise_id = exercise.id
                                    }
                                    iterate.set(oldVal)
                                }
                            })
                                .subscribeOn(Schedulers.io())
                        else
                            Completable.complete()
                    )
                    .andThen(
                        if (exercise.muscles_secondary != null)
                            exercisesDao.insertMuscles(exercise.muscles_secondary!!.toMutableList().apply {
                                val iterate = listIterator()
                                while (iterate.hasNext()) {
                                    val oldVal = iterate.next().apply {
                                        isSecondary = true
                                        exercise_id = exercise.id
                                    }
                                    iterate.set(oldVal)
                                }
                            })
                                .subscribeOn(Schedulers.io())
                        else
                            Completable.complete()
                    )
            )
    }

    override fun getExercises(): Maybe<List<ExerciseDetails>> {
        return exercisesDao.selectAllExercises()
            .flatMap { exercisesList ->
                exercisesDao.selectCategories()
                    .map { categoriesList ->
                        exercisesList.onEach { exerciseData ->
                            exerciseData.category = categoriesList.find { categoryData ->
                                categoryData.id == exerciseData.category_id
                            } ?: ExercisesCategory()
                        }
                    }
            }
            .flatMap { exercisesList ->
                exercisesDao.selectAllEquipments()
                    .map { equipmentList ->
                        exercisesList.onEach { exerciseData ->
                            val newEquipmentList: MutableList<ExerciseEquipment> = mutableListOf<ExerciseEquipment>()
                            equipmentList.forEach { equipmentData ->
                                if (equipmentData.exercise_id == exerciseData.id)
                                    newEquipmentList.add(equipmentData)
                            }
                            if (newEquipmentList.isNotEmpty())
                                exerciseData.equipment = newEquipmentList
                        }
                    }
            }
            .flatMap { exercisesList ->
                exercisesDao.selectAllMuscles()
                    .map { musclesList ->
                        exercisesList.onEach { exerciseData ->
                            val newMusclesList: MutableList<ExerciseMuscle> = mutableListOf<ExerciseMuscle>()
                            val newSecondaryMusclesList: MutableList<ExerciseMuscle> = mutableListOf<ExerciseMuscle>()
                            musclesList.forEach { muscleData ->
                                if (muscleData.exercise_id == exerciseData.id)
                                    if (muscleData.isSecondary)
                                        newSecondaryMusclesList.add(muscleData)
                                    else
                                        newMusclesList.add(muscleData)
                            }
                            if (newMusclesList.isNotEmpty())
                                exerciseData.muscles = newMusclesList
                            if (newSecondaryMusclesList.isNotEmpty())
                                exerciseData.muscles_secondary = newSecondaryMusclesList
                        }
                    }
            }
            .subscribeOn(Schedulers.io())
    }

}
