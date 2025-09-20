package com.softwavegames.tasksmanagement.model

import org.junit.Assert.*
import org.junit.Test

class TaskTest {

    @Test
    fun `Task should be created with all required fields`() {
        val task = Task(
            id = "test-id",
            Title = "Test Task",
            Description = "Test Description",
            DueDate = "2024-01-15",
            TargetDate = "2024-01-16",
            Priority = 1,
            isResolved = false
        )

        assertEquals("test-id", task.id)
        assertEquals("Test Task", task.Title)
        assertEquals("Test Description", task.Description)
        assertEquals("2024-01-15", task.DueDate)
        assertEquals("2024-01-16", task.TargetDate)
        assertEquals(1, task.Priority)
        assertEquals(false, task.isResolved)
    }

    @Test
    fun `Task should handle null dates correctly`() {
        val task = Task(
            id = "test-id",
            Title = "Test Task",
            Description = "Test Description",
            DueDate = null,
            TargetDate = null,
            Priority = 1,
            isResolved = false
        )

        assertEquals("test-id", task.id)
        assertEquals("Test Task", task.Title)
        assertEquals("Test Description", task.Description)
        assertNull(task.DueDate)
        assertNull(task.TargetDate)
        assertEquals(1, task.Priority)
        assertEquals(false, task.isResolved)
    }

    @Test
    fun `Task should have default isResolved value of false`() {
        val task = Task(
            id = "test-id",
            Title = "Test Task",
            Description = "Test Description",
            DueDate = "2024-01-15",
            TargetDate = "2024-01-16",
            Priority = 1
        )

        assertEquals(false, task.isResolved)
    }
}