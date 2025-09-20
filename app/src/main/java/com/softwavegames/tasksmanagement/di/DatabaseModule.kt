package com.softwavegames.tasksmanagement.di

import android.content.Context
import androidx.room.Room
import com.softwavegames.tasksmanagement.data.local.TaskDao
import com.softwavegames.tasksmanagement.data.local.TasksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideTasksDatabase(
        @ApplicationContext context: Context
    ): TasksDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = TasksDatabase::class.java,
            name = TasksDatabase.DB_NAME
        )
        .fallbackToDestructiveMigration(false)
        .build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(
        tasksDatabase: TasksDatabase
    ): TaskDao = tasksDatabase.taskDao()
}
