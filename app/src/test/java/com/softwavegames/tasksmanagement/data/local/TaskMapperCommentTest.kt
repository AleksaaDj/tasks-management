package com.softwavegames.tasksmanagement.data.local

import com.softwavegames.tasksmanagement.data.model.Task
import com.softwavegames.tasksmanagement.data.model.TaskStatus
import com.softwavegames.tasksmanagement.data.local.TaskMapper.toEntity
import com.softwavegames.tasksmanagement.data.local.TaskMapper.toDomain
import org.junit.Test
import org.junit.Assert.*

class TaskMapperCommentTest {

    @Test
    fun `Task toEntity includes comment field`() {
        val task = Task(
            Description = "Test description",
            DueDate = "2025-01-01",
            Priority = 1,
            TargetDate = "2025-01-01",
            Title = "Test task",
            id = "test-id",
            isResolved = false,
            status = TaskStatus.UNRESOLVED,
            comment = "Test comment"
        )

        val entity = task.toEntity()

        assertEquals("Test comment", entity.comment)
    }

    @Test
    fun `Task toEntity handles null comment`() {
        val task = Task(
            Description = "Test description",
            DueDate = "2025-01-01",
            Priority = 1,
            TargetDate = "2025-01-01",
            Title = "Test task",
            id = "test-id",
            isResolved = false,
            status = TaskStatus.UNRESOLVED,
            comment = null
        )

        val entity = task.toEntity()

        assertNull(entity.comment)
    }

    @Test
    fun `TaskEntity toDomain includes comment field`() {
        val entity = TaskEntity(
            localId = "local-id",
            apiId = "api-id",
            title = "Test task",
            description = "Test description",
            dueDate = "2025-01-01",
            targetDate = "2025-01-01",
            priority = 1,
            isResolved = false,
            status = "UNRESOLVED",
            comment = "Test comment"
        )

        val task = entity.toDomain()

        assertEquals("Test comment", task.comment)
    }

    @Test
    fun `TaskEntity toDomain handles null comment`() {
        val entity = TaskEntity(
            localId = "local-id",
            apiId = "api-id",
            title = "Test task",
            description = "Test description",
            dueDate = "2025-01-01",
            targetDate = "2025-01-01",
            priority = 1,
            isResolved = false,
            status = "UNRESOLVED",
            comment = null
        )

        val task = entity.toDomain()

        assertNull(task.comment)
    }

    @Test
    fun `TaskEntity toDomain handles empty comment`() {
        val entity = TaskEntity(
            localId = "local-id",
            apiId = "api-id",
            title = "Test task",
            description = "Test description",
            dueDate = "2025-01-01",
            targetDate = "2025-01-01",
            priority = 1,
            isResolved = false,
            status = "UNRESOLVED",
            comment = ""
        )

        val task = entity.toDomain()

        assertEquals("", task.comment)
    }
}
