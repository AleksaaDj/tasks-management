package com.softwavegames.tasksmanagement.data.local

import com.softwavegames.tasksmanagement.data.local.TaskMapper.toDomain
import com.softwavegames.tasksmanagement.data.local.TaskMapper.toEntity
import com.softwavegames.tasksmanagement.data.model.Task
import com.softwavegames.tasksmanagement.data.model.TaskStatus
import org.junit.Test
import org.junit.Assert.*

class TaskMapperTest {

    @Test
    fun `Task toEntity should handle default status correctly`() {
        val task = Task(
            id = "test-id",
            Title = "Test Task",
            Description = "Test Description",
            DueDate = "2025-09-21",
            TargetDate = "2025-09-21",
            Priority = 1,
            isResolved = false
        )

        val entity = task.toEntity()

        assertEquals("UNRESOLVED", entity.status)
        assertEquals("test-id", entity.apiId)
        assertEquals("Test Task", entity.title)
        assertEquals("Test Description", entity.description)
        assertEquals("2025-09-21", entity.dueDate)
        assertEquals("2025-09-21", entity.targetDate)
        assertEquals(1, entity.priority)
        assertEquals(false, entity.isResolved)
    }

    @Test
    fun `Task toEntity should handle valid status correctly`() {
        val task = Task(
            id = "test-id",
            Title = "Test Task",
            Description = "Test Description",
            DueDate = "2025-09-21",
            TargetDate = "2025-09-21",
            Priority = 1,
            isResolved = false,
            status = TaskStatus.RESOLVED
        )

        val entity = task.toEntity()

        assertEquals("RESOLVED", entity.status)
        assertEquals("test-id", entity.apiId)
        assertEquals("Test Task", entity.title)
        assertEquals("Test Description", entity.description)
        assertEquals("2025-09-21", entity.dueDate)
        assertEquals("2025-09-21", entity.targetDate)
        assertEquals(1, entity.priority)
        assertEquals(false, entity.isResolved)
    }

    @Test
    fun `TaskEntity toDomain should handle empty status correctly`() {
        val entity = TaskEntity(
            localId = "local-id",
            apiId = "test-id",
            title = "Test Task",
            description = "Test Description",
            dueDate = "2025-09-21",
            targetDate = "2025-09-21",
            priority = 1,
            isResolved = false,
            status = ""
        )

        val task = entity.toDomain()

        assertEquals(TaskStatus.UNRESOLVED, task.status)
        assertEquals("test-id", task.id)
        assertEquals("Test Task", task.Title)
        assertEquals("Test Description", task.Description)
        assertEquals("2025-09-21", task.DueDate)
        assertEquals("2025-09-21", task.TargetDate)
        assertEquals(1, task.Priority)
        assertEquals(false, task.isResolved)
    }

    @Test
    fun `TaskEntity toDomain should handle invalid status correctly`() {
        val entity = TaskEntity(
            localId = "local-id",
            apiId = "test-id",
            title = "Test Task",
            description = "Test Description",
            dueDate = "2025-09-21",
            targetDate = "2025-09-21",
            priority = 1,
            isResolved = false,
            status = "INVALID_STATUS"
        )

        val task = entity.toDomain()

        assertEquals(TaskStatus.UNRESOLVED, task.status)
        assertEquals("test-id", task.id)
        assertEquals("Test Task", task.Title)
        assertEquals("Test Description", task.Description)
        assertEquals("2025-09-21", task.DueDate)
        assertEquals("2025-09-21", task.TargetDate)
        assertEquals(1, task.Priority)
        assertEquals(false, task.isResolved)
    }

    @Test
    fun `TaskEntity toDomain should handle valid status correctly`() {
        val entity = TaskEntity(
            localId = "local-id",
            apiId = "test-id",
            title = "Test Task",
            description = "Test Description",
            dueDate = "2025-09-21",
            targetDate = "2025-09-21",
            priority = 1,
            isResolved = false,
            status = "CANT_RESOLVE"
        )

        val task = entity.toDomain()

        assertEquals(TaskStatus.CANT_RESOLVE, task.status)
        assertEquals("test-id", task.id)
        assertEquals("Test Task", task.Title)
        assertEquals("Test Description", task.Description)
        assertEquals("2025-09-21", task.DueDate)
        assertEquals("2025-09-21", task.TargetDate)
        assertEquals(1, task.Priority)
        assertEquals(false, task.isResolved)
    }

    @Test
    fun `TaskEntity toDomain should handle empty string status correctly`() {
        val entity = TaskEntity(
            localId = "local-id",
            apiId = "test-id",
            title = "Test Task",
            description = "Test Description",
            dueDate = "2025-09-21",
            targetDate = "2025-09-21",
            priority = 1,
            isResolved = false,
            status = ""
        )

        val task = entity.toDomain()

        assertEquals(TaskStatus.UNRESOLVED, task.status)
        assertEquals("test-id", task.id)
        assertEquals("Test Task", task.Title)
        assertEquals("Test Description", task.Description)
        assertEquals("2025-09-21", task.DueDate)
        assertEquals("2025-09-21", task.TargetDate)
        assertEquals(1, task.Priority)
        assertEquals(false, task.isResolved)
    }

    @Test
    fun `List TaskEntity toDomain should convert all entities correctly`() {
        val entities = listOf(
            TaskEntity(
                localId = "local-1",
                apiId = "test-1",
                title = "Task 1",
                description = "Description 1",
                dueDate = "2025-09-21",
                targetDate = "2025-09-21",
                priority = 1,
                isResolved = false,
                status = "UNRESOLVED"
            ),
            TaskEntity(
                localId = "local-2",
                apiId = "test-2",
                title = "Task 2",
                description = "Description 2",
                dueDate = "2025-09-22",
                targetDate = "2025-09-22",
                priority = 2,
                isResolved = true,
                status = "RESOLVED"
            )
        )

        val tasks = entities.toDomain()

        assertEquals(2, tasks.size)
        assertEquals(TaskStatus.UNRESOLVED, tasks[0].status)
        assertEquals("test-1", tasks[0].id)
        assertEquals("Task 1", tasks[0].Title)
        assertEquals(TaskStatus.RESOLVED, tasks[1].status)
        assertEquals("test-2", tasks[1].id)
        assertEquals("Task 2", tasks[1].Title)
    }
}