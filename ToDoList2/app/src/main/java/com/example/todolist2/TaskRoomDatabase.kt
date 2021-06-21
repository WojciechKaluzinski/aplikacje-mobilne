package com.example.todolist2


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Date
import java.time.LocalDateTime
import java.util.*

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */
@Database(entities = [Task::class], version = 1)
@TypeConverters(Converters::class)
abstract class TaskRoomDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): TaskRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskRoomDatabase::class.java,
                    "task_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(TaskDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class TaskDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.taskDao())
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun populateDatabase(taskDao: TaskDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            taskDao.deleteAll()

            var task = Task(1, "Hello", LocalDateTime.of(2020, 2, 20, 12, 13), LocalDateTime.of(2020, 2, 22, 12, 15), "To jest dłuższy opis tego trudnego i wymagającego zadania", Images.STAR, "1", "1")
            taskDao.insert(task)
            task = Task(2, "World!", LocalDateTime.of(2020, 2, 20, 12, 13), LocalDateTime.of(2020, 2, 22, 12, 15), "To jest dłuższy opis tego trudnego i wymagającego zadania", Images.CHECK, "1", "1")
            taskDao.insert(task)
        }
    }
}