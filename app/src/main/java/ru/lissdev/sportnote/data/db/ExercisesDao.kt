package ru.lissdev.sportnote.data.db

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import ru.lissdev.sportnote.data.model.*

@Dao
interface ExercisesDao {

    // INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExercise(exercise: ExerciseDetails): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(category: ExercisesCategory): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEquipment(equipmentList: List<ExerciseEquipment>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMuscles(muscleList: List<ExerciseMuscle>): Completable

    // SELECT
    @Query("SELECT * FROM exercise_table")
    fun selectAllExercises(): Maybe<List<ExerciseDetails>>

    @Query("SELECT * FROM exercise_table WHERE category_id = :categoryId")
    fun selectExercises(categoryId: Long): Maybe<List<ExerciseDetails>>

    @Query("SELECT * FROM category_table")
    fun selectCategories(): Maybe<List<ExercisesCategory>>

    @Query("SELECT * FROM category_table WHERE id = :id")
    fun selectCategory(id: Long): Maybe<ExercisesCategory>

    @Query("SELECT * FROM equipment_table")
    fun selectAllEquipments(): Maybe<List<ExerciseEquipment>>

    @Query("SELECT * FROM equipment_table WHERE exercise_id = :exerciseId")
    fun selectEquipments(exerciseId: Long): Maybe<List<ExerciseEquipment>>

    @Query("SELECT * FROM muscles_table")
    fun selectAllMuscles(): Maybe<List<ExerciseMuscle>>

    @Query("SELECT * FROM muscles_table WHERE exercise_id = :exerciseId")
    fun selectMuscles(exerciseId: Long): Maybe<List<ExerciseMuscle>>

    @Query("SELECT * FROM muscles_table WHERE exercise_id = :exerciseId")
    fun selectSecondaryMuscles(exerciseId: Long): Maybe<List<ExerciseMuscle>>

    // DELETE
    @Query("DELETE FROM exercise_table WHERE id = :id")
    fun deleteExercise(id: Long): Completable

    @Query("DELETE FROM category_table WHERE id = :id")
    fun deleteCategory(id: Long): Completable

    @Query("DELETE FROM equipment_table WHERE id = :id")
    fun deleteEquipment(id: Long): Completable

    @Query("DELETE FROM muscles_table WHERE id = :id")
    fun deleteMuscles(id: Long): Completable

}
