package com.softwavegames.tasksmanagement.di

import android.content.Context
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
        return TasksDatabase.create(context)
    }

    @Provides
    @Singleton
    fun provideTaskDao(
        tasksDatabase: TasksDatabase
    ): TaskDao = tasksDatabase.taskDao()
}
