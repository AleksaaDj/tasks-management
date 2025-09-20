package com.softwavegames.tasksmanagement.data

import com.softwavegames.tasksmanagement.data.local.TaskDao
import com.softwavegames.tasksmanagement.data.local.TaskMapper.toDomain
import com.softwavegames.tasksmanagement.data.local.TaskMapper.toEntity
import com.softwavegames.tasksmanagement.data.remote.TasksApi
import com.softwavegames.tasksmanagement.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TasksRepository @Inject constructor(
    private val tasksApi: TasksApi,
    private val taskDao: TaskDao
) {
    
    suspend fun initializeDatabase(): Result<Unit> {
        return try {
            val response = tasksApi.getTasks()
            val taskEntities = response.tasks.map { it.toEntity() }
            taskDao.insertTasks(taskEntities)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun getTasksByDate(date: String): Flow<List<Task>> {
        return taskDao.getTasksByDate(date).map { it.toDomain() }
    }
    
    suspend fun updateTaskResolvedStatus(localId: String, isResolved: Boolean): Result<Unit> {
        return try {
            taskDao.updateTaskResolvedStatus(localId, isResolved)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun isDatabaseEmpty(): Boolean {
        return taskDao.getTaskCount() == 0
    }
}