package ru.lissdev.sportnote.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import ru.lissdev.sportnote.data.db.AppDatabase

@Entity(tableName = AppDatabase.EXERCISE_TABLE)
data class ExerciseDetails(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = COLUMN_ID)
    var id: Long = 0L,
    @Ignore
    var exercise_base_id: Long = 0L,
    @ColumnInfo(name = COLUMN_NAME)
    var name: String = "",
    @ColumnInfo(name = COLUMN_DESCRIPTION)
    var description: String = "",
    @Ignore
    var category: ExercisesCategory = ExercisesCategory(),
    @Ignore
    var equipment: List<ExerciseEquipment>? = emptyList(),
    @Ignore
    var muscles: List<ExerciseMuscle>? = emptyList(),
    @Ignore
    var muscles_secondary: List<ExerciseMuscle>? = emptyList(),
    @Ignore
    var images: List<ExerciseImages>? = emptyList(),

    // for DB
    @ColumnInfo(name = COLUMN_CATEGORY_ID)
    var category_id: Long = 0L
) {

    companion object {
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_CATEGORY_ID = "category_id"
    }
}
