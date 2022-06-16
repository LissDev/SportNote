package ru.lissdev.sportnote.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.lissdev.sportnote.data.db.AppDatabase

@Entity(tableName = AppDatabase.MUSCLES_TABLE)
data class ExerciseMuscle(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = COLUMN_ID)
    var id: Long = 0L,
    @ColumnInfo(name = COLUMN_NAME)
    var name: String = "",
    @ColumnInfo(name = COLUMN_NAME_EN)
    var name_en: String = "",
    @ColumnInfo(name = COLUMN_IMAGE_MAIN)
    var image_url_main: String = "",
    @ColumnInfo(name = COLUMN_IMAGE_SECONDARY)
    var image_url_secondary: String = "",

    // for DB
    @ColumnInfo(name = COLUMN_EXERCISE_ID)
    var exercise_id: Long = 0L,
    @ColumnInfo(name = COLUMN_IS_SECONDARY)
    var isSecondary: Boolean = false
) {

    companion object {
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_NAME_EN = "name_en"
        const val COLUMN_IMAGE_MAIN = "image_url_main"
        const val COLUMN_IMAGE_SECONDARY = "image_url_secondary"
        const val COLUMN_EXERCISE_ID = "exercise_id"
        const val COLUMN_IS_SECONDARY = "is_secondary"
    }
}
