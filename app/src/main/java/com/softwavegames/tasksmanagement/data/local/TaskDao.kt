package com.softwavegames.tasksmanagement.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks WHERE dueDate = :date ORDER BY priority DESC, dueDate ASC")
    fun getTasksByDate(date: String): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE apiId = :taskId LIMIT 1")
    suspend fun getTaskById(taskId: String): TaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(tasks: List<TaskEntity>)
    
    @Query("UPDATE tasks SET status = :status, isResolved = :isResolved WHERE apiId = :taskId")
    suspend fun updateTaskStatus(taskId: String, status: String, isResolved: Boolean)
    
    @Query("UPDATE tasks SET status = :status, isResolved = :isResolved, comment = :comment WHERE apiId = :taskId")
    suspend fun updateTaskStatusWithComment(taskId: String, status: String, isResolved: Boolean, comment: String?)
    
    @Query("SELECT COUNT(*) FROM tasks")
    suspend fun getTaskCount(): Int
}
