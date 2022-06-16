package ru.lissdev.sportnote.data.db

import android.app.Application
import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.lissdev.sportnote.data.model.ExerciseDetails
import ru.lissdev.sportnote.data.model.ExerciseEquipment
import ru.lissdev.sportnote.data.model.ExerciseMuscle
import ru.lissdev.sportnote.data.model.ExercisesCategory

@Database(
    version = 2,
    exportSchema = false,
    entities = [
        ExerciseDetails::class,
        ExercisesCategory::class,
        ExerciseEquipment::class,
        ExerciseMuscle::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        private const val databaseName = "sportNoteDatabase"
        const val EXERCISE_TABLE = "exercise_table"
        const val CATEGORY_TABLE = "category_table"
        const val EQUIPMENT_TABLE = "equipment_table"
        const val MUSCLES_TABLE = "muscles_table"

//        @Volatile
//        private var instance: AppDatabase? = null
//
//        fun getInstance(application: Application): AppDatabase {
//            synchronized(this) {
//                return instance ?: instance ?: createDatabaseInstance(application)
//            }
//        }

        private fun migrationList() = arrayOf(
            object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL(
                        "CREATE TABLE $EXERCISE_TABLE (" +
                                "${ExerciseDetails.COLUMN_ID} INTEGER NOT NULL PRIMARY KEY," +
                                "${ExerciseDetails.COLUMN_CATEGORY_ID} INTEGER NOT NULL REFERENCES $CATEGORY_TABLE(${ExercisesCategory.COLUMN_ID}) ON DELETE CASCADE," +
                                "${ExerciseDetails.COLUMN_NAME} TEXT NOT NULL," +
                                "${ExerciseDetails.COLUMN_DESCRIPTION} TEXT" +
                                ")"
                    )
                    database.execSQL(
                        "CREATE TABLE $CATEGORY_TABLE (" +
                                "${ExercisesCategory.COLUMN_ID} INTEGER NOT NULL PRIMARY KEY," +
                                "${ExercisesCategory.COLUMN_NAME} TEXT NOT NULL" +
                                ")"
                    )
                    database.execSQL(
                        "CREATE TABLE $EQUIPMENT_TABLE (" +
                                "${ExerciseEquipment.COLUMN_ID} INTEGER NOT NULL PRIMARY KEY," +
                                "${ExerciseEquipment.COLUMN_EXERCISE_ID} INTEGER NOT NULL REFERENCES $EXERCISE_TABLE(${ExerciseDetails.COLUMN_ID}) ON DELETE CASCADE," +
                                "${ExerciseEquipment.COLUMN_NAME} TEXT NOT NULL" +
                                ")"
                    )
                    database.execSQL(
                        "CREATE TABLE $MUSCLES_TABLE (" +
                                "${ExerciseMuscle.COLUMN_ID} INTEGER NOT NULL PRIMARY KEY," +
                                "${ExerciseMuscle.COLUMN_EXERCISE_ID} INTEGER NOT NULL REFERENCES $EXERCISE_TABLE(${ExerciseDetails.COLUMN_ID}) ON DELETE CASCADE," +
                                "${ExerciseMuscle.COLUMN_NAME} TEXT NOT NULL," +
                                "${ExerciseMuscle.COLUMN_NAME_EN} TEXT," +
                                "${ExerciseMuscle.COLUMN_IMAGE_MAIN} TEXT," +
                                "${ExerciseMuscle.COLUMN_IMAGE_SECONDARY} TEXT," +
                                "${ExerciseMuscle.COLUMN_IS_SECONDARY} INTEGER NOT NULL" +
                                ")"
                    )
                }
            }
        )

        fun createDatabaseInstance(appContext: Context): AppDatabase {
            return Room
                .databaseBuilder(
                    appContext,
                    AppDatabase::class.java,
                    databaseName
                )
                .addMigrations(*migrationList())
                .build()
        }
    }

    abstract fun exercisesDao(): ExercisesDao
}
