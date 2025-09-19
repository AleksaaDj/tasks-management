package com.softwavegames.tasksmanagement.model

import org.junit.Test
import org.junit.Assert.*

class TaskTest {

    @Test
    fun `task creation with all properties should work correctly`() {
        // Given
        val task = Task(
            Description = "Find us some cool place for team building - place reservation for December 5th",
            DueDate = "2025-09-15",
            Priority = 0,
            TargetDate = "2025-09-15",
            Title = "Organize team building for mobile team",
            id = "a4a044856fca4362a8b72070329c9afd"
        )

        // Then
        assertEquals("Find us some cool place for team building - place reservation for December 5th", task.Description)
        assertEquals("2025-09-15", task.DueDate)
        assertEquals(0, task.Priority)
        assertEquals("2025-09-15", task.TargetDate)
        assertEquals("Organize team building for mobile team", task.Title)
        assertEquals("a4a044856fca4362a8b72070329c9afd", task.id)
    }

    @Test
    fun `task equality should work correctly`() {
        // Given
        val task1 = Task(
            Description = "Test description",
            DueDate = "2025-09-15",
            Priority = 0,
            TargetDate = "2025-09-15",
            Title = "Test Task",
            id = "same-id"
        )

        val task2 = Task(
            Description = "Test description",
            DueDate = "2025-09-15",
            Priority = 0,
            TargetDate = "2025-09-15",
            Title = "Test Task",
            id = "same-id"
        )

        val task3 = Task(
            Description = "Different description",
            DueDate = "2025-09-15",
            Priority = 0,
            TargetDate = "2025-09-15",
            Title = "Test Task",
            id = "same-id"
        )

        // Then
        assertEquals(task1, task2)
        assertNotEquals(task1, task3)
        assertEquals(task1.hashCode(), task2.hashCode())
    }

    @Test
    fun `task with different id should not be equal`() {
        // Given
        val task1 = Task(
            Description = "Same description",
            DueDate = "2025-09-15",
            Priority = 0,
            TargetDate = "2025-09-15",
            Title = "Same Title",
            id = "id-1"
        )

        val task2 = Task(
            Description = "Same description",
            DueDate = "2025-09-15",
            Priority = 0,
            TargetDate = "2025-09-15",
            Title = "Same Title",
            id = "id-2"
        )

        // Then
        assertNotEquals(task1, task2)
    }

    @Test
    fun `task with priority should be identified correctly`() {
        // Given
        val taskWithPriority = Task(
            Description = "Important task",
            DueDate = "2025-09-15",
            Priority = 0, // Based on API response, priority starts from 0
            TargetDate = "2025-09-15",
            Title = "Important Task",
            id = "important-1"
        )

        // Then
        assertTrue(taskWithPriority.Priority >= 0)
    }
}
