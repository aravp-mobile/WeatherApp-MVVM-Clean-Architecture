package com.dev.aravp.weatherapp_mvvm_clean_architecture.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.work.impl.Migration_1_2
import com.dev.aravp.weatherapp_mvvm_clean_architecture.data.local.dao.FileDao
import com.dev.aravp.weatherapp_mvvm_clean_architecture.data.local.dao.TaskDao
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.model.FileEntity
import com.dev.aravp.weatherapp_mvvm_clean_architecture.domain.model.Task


/**
 * @Database annotation tells Room to generate the database using :
 *  @Task as Entity table
 *  @TaskDao as the Dao interface
 *  version as 1 of the database
 */
@Database(
    entities = [Task::class, FileEntity::class],
    version = 2,
    exportSchema = false
)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun fileDao(): FileDao

    companion object {
        @Volatile private var INSTANCE: TaskDatabase? = null

        fun getInstance(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_db"
                )
                    .addMigrations(Migration_1_2) // ✅ Migration added
                    .fallbackToDestructiveMigrationOnDowngrade(false) // Optional dev safety
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}



/*
@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        private var INSTANCE: TaskDatabase? = null

        */
/**
         * The getInstance(context) method returns the singleton instance of the database. It ensures
         * only one instance of the database is existed throughout the app's lifecycle.
         * 1) Prevents multiple Room instances from being created
         * 2) Uses synchronized block for thread safety
         * 3) Is manually used in traditional non-DI apps.
         *//*

        fun getInstance(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}*/
