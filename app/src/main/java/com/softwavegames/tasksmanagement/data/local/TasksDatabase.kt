package com.softwavegames.tasksmanagement.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TaskEntity::class],
    version = 1,
    exportSchema = true
)
abstract class TasksDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    
    companion object {
        const val DB_NAME = "tasks_database"
    }
}
