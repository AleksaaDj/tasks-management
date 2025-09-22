package com.softwavegames.tasksmanagement.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(
    entities = [TaskEntity::class],
    version = 3,
    exportSchema = true
)
abstract class TasksDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    
    companion object {
        const val DB_NAME = "tasks_database"
        
        fun create(context: Context): TasksDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                TasksDatabase::class.java,
                DB_NAME
            )
                .fallbackToDestructiveMigration(true)
                .build()
        }
    }
}
