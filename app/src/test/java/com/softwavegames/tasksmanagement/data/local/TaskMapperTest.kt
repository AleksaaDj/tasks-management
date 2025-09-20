package com.softwavegames.tasksmanagement.data.local

import com.softwavegames.tasksmanagement.model.Task
import com.softwavegames.tasksmanagement.data.local.TaskMapper.toEntity
import com.softwavegames.tasksmanagement.data.local.TaskMapper.toDomain
import org.junit.Assert.*
import org.junit.Test

class TaskMapperTest {

    @Test
    fun `Task to TaskEntity conversion is correct`() {
        val task = Task(
            Description = "Test Description",
            DueDate = "2024-07-20",
            Priority = 1,
            TargetDate = "2024-07-21",
            Title = "Test Title",
            id = "api-id-123",
            isResolved = false
        )

        val entity = task.toEntity()

        assertNotNull(entity.localId)
        assertTrue(entity.localId.isNotEmpty())
        assertEquals(task.id, entity.apiId)
        assertEquals(task.Title, entity.title)
        assertEquals(task.Description, entity.description)
        assertEquals(task.DueDate, entity.dueDate)
        assertEquals(task.TargetDate, entity.targetDate)
        assertEquals(task.Priority, entity.priority)
        assertEquals(task.isResolved, entity.isResolved)
    }

    @Test
    fun `TaskEntity to Task conversion is correct`() {
        val entity = TaskEntity(
            localId = "local-id-456",
            apiId = "api-id-123",
            title = "Test Title",
            description = "Test Description",
            dueDate = "2024-07-20",
            targetDate = "2024-07-21",
            priority = 1,
            isResolved = true
        )

        val task = entity.toDomain()

        assertEquals(entity.apiId, task.id)
        assertEquals(entity.title, task.Title)
        assertEquals(entity.description, task.Description)
        assertEquals(entity.dueDate, task.DueDate)
        assertEquals(entity.targetDate, task.TargetDate)
        assertEquals(entity.priority, task.Priority)
        assertEquals(entity.isResolved, task.isResolved)
    }

    @Test
    fun `List of TaskEntity to List of Task conversion is correct`() {
        val entities = listOf(
            TaskEntity("l1", "a1", "T1", "D1", "2024-07-20", "2024-07-20", 1, false),
            TaskEntity("l2", "a2", "T2", "D2", "2024-07-21", "2024-07-21", 2, true)
        )

        val tasks = entities.toDomain()

        assertEquals(entities.size, tasks.size)
        assertEquals(entities[0].apiId, tasks[0].id)
        assertEquals(entities[1].title, tasks[1].Title)
    }

    @Test
    fun `Task to TaskEntity handles null dates`() {
        val task = Task(
            Description = "No Date Task",
            DueDate = null,
            Priority = 0,
            TargetDate = null,
            Title = "Null Date",
            id = "api-id-null",
            isResolved = false
        )

        val entity = task.toEntity()

        assertEquals(task.DueDate, entity.dueDate)
        assertEquals(task.TargetDate, entity.targetDate)
    }

    @Test
    fun `TaskEntity to Task handles null dates`() {
        val entity = TaskEntity(
            localId = "local-id-null",
            apiId = "api-id-null",
            title = "Null Date",
            description = "No Date Task",
            dueDate = null,
            targetDate = null,
            priority = 0,
            isResolved = false
        )

        val task = entity.toDomain()

        assertEquals(entity.dueDate, task.DueDate)
        assertEquals(entity.targetDate, task.TargetDate)
    }
}