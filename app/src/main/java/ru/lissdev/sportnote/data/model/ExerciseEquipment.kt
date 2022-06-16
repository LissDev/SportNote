package ru.lissdev.sportnote.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.lissdev.sportnote.data.db.AppDatabase

@Entity(tableName = AppDatabase.EQUIPMENT_TABLE)
data class ExerciseEquipment(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = COLUMN_ID)
    var id: Long = 0L,
    @ColumnInfo(name = COLUMN_NAME)
    var name: String = "",

    // for DB
    @ColumnInfo(name = COLUMN_EXERCISE_ID)
    var exercise_id: Long = 0L
) {

    companion object {
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_EXERCISE_ID = "exercise_id"
    }
}
