package com.softwavegames.tasksmanagement.data

import com.softwavegames.tasksmanagement.data.local.TaskDao
import com.softwavegames.tasksmanagement.data.local.TaskMapper.toDomain
import com.softwavegames.tasksmanagement.data.local.TaskMapper.toEntity
import com.softwavegames.tasksmanagement.data.remote.TasksApi
import com.softwavegames.tasksmanagement.data.model.Task
import com.softwavegames.tasksmanagement.data.model.TaskStatus
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
            val taskEntities = response.tasks.mapNotNull { task ->
                try {
                    task.toEntity()
                } catch (_: Exception) {
                    null
                }
            }
            taskDao.insertTasks(taskEntities)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun getTasksByDate(date: String): Flow<List<Task>> {
        return taskDao.getTasksByDate(date).map { it.toDomain() }
    }
    
    suspend fun getTaskById(taskId: String): Task? {
        return taskDao.getTaskById(taskId)?.toDomain()
    }
    
    suspend fun isDatabaseEmpty(): Boolean {
        return taskDao.getTaskCount() == 0
    }
    
    suspend fun updateTaskStatus(taskId: String, status: TaskStatus): Result<Unit> {
        return try {
            val isResolved = status == TaskStatus.RESOLVED
            taskDao.updateTaskStatus(taskId, status.name, isResolved)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updateTaskStatusWithComment(taskId: String, status: TaskStatus, comment: String?): Result<Unit> {
        return try {
            val isResolved = status == TaskStatus.RESOLVED
            taskDao.updateTaskStatusWithComment(taskId, status.name, isResolved, comment)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}