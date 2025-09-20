package com.softwavegames.tasksmanagement.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val localId: String,
    val apiId: String,
    val title: String,
    val description: String,
    val dueDate: String?,
    val targetDate: String?,
    val priority: Int,
    val isResolved: Boolean = false
)
