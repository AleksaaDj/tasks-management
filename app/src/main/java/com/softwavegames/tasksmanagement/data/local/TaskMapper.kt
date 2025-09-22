package com.softwavegames.tasksmanagement.data.local

import com.softwavegames.tasksmanagement.data.model.Task
import com.softwavegames.tasksmanagement.data.model.TaskStatus
import java.util.UUID

object TaskMapper {
    
    fun Task.toEntity(): TaskEntity {
        return TaskEntity(
            localId = UUID.randomUUID().toString(),
            apiId = this.id,
            title = this.Title,
            description = this.Description,
            dueDate = this.DueDate,
            targetDate = this.TargetDate,
            priority = this.Priority,
            isResolved = this.isResolved,
            status = this.status.name,
            comment = this.comment
        )
    }
    
    fun TaskEntity.toDomain(): Task {
        return Task(
            id = this.apiId,
            Title = this.title,
            Description = this.description,
            DueDate = this.dueDate,
            TargetDate = this.targetDate,
            Priority = this.priority,
            isResolved = this.isResolved,
            status = try {
                TaskStatus.valueOf(this.status)
            } catch (_: IllegalArgumentException) {
                TaskStatus.UNRESOLVED
            },
            comment = this.comment
        )
    }
    
    fun List<TaskEntity>.toDomain(): List<Task> {
        return this.map { it.toDomain() }
    }
}
