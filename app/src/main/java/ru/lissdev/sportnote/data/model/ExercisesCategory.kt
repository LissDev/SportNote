package ru.lissdev.sportnote.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.lissdev.sportnote.data.db.AppDatabase

@Entity(tableName = AppDatabase.CATEGORY_TABLE)
data class ExercisesCategory (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = COLUMN_ID)
    var id: Long = 0L,
    @ColumnInfo(name = COLUMN_NAME)
    var name: String = ""
) {

    companion object {
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
    }
}