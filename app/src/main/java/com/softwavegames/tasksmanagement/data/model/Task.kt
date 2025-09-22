package com.softwavegames.tasksmanagement.data.model

data class Task(
    val Description: String,
    val DueDate: String?,
    val Priority: Int,
    val TargetDate: String?,
    val Title: String,
    val id: String,
    val isResolved: Boolean = false,
    val status: TaskStatus = TaskStatus.UNRESOLVED,
    val comment: String? = null
)

enum class TaskStatus {
    UNRESOLVED,
    RESOLVED,
    CANT_RESOLVE
}