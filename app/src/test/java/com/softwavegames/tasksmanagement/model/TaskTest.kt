package com.softwavegames.tasksmanagement.model

import org.junit.Test
import org.junit.Assert.*

class TaskTest {

    @Test
    fun `task creation with all properties should work correctly`() {
        val task = Task(
            Description = "Find us some cool place for team building - place reservation for December 5th",
            DueDate = "2025-09-15",
            Priority = 0,
            TargetDate = "2025-09-15",
            Title = "Organize team building for mobile team",
            id = "a4a044856fca4362a8b72070329c9afd"
        )

        assertEquals("Find us some cool place for team building - place reservation for December 5th", task.Description)
        assertEquals("2025-09-15", task.DueDate)
        assertEquals(0, task.Priority)
        assertEquals("2025-09-15", task.TargetDate)
        assertEquals("Organize team building for mobile team", task.Title)
        assertEquals("a4a044856fca4362a8b72070329c9afd", task.id)
        assertFalse(task.isResolved) // Default should be false
    }

    @Test
    fun `task equality should work correctly`() {
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

        assertEquals(task1, task2)
        assertNotEquals(task1, task3)
        assertEquals(task1.hashCode(), task2.hashCode())
    }

    @Test
    fun `task with different id should not be equal`() {
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

        assertNotEquals(task1, task2)
    }

    @Test
    fun `task with priority should be identified correctly`() {
        val taskWithPriority = Task(
            Description = "Important task",
            DueDate = "2025-09-15",
            Priority = 0, // Based on API response, priority starts from 0
            TargetDate = "2025-09-15",
            Title = "Important Task",
            id = "important-1"
        )

        assertTrue(taskWithPriority.Priority >= 0)
    }

    @Test
    fun `task defaults to unresolved when isResolved not specified`() {
        val task = Task(
            Description = "Test task",
            DueDate = "2025-09-15",
            Priority = 0,
            TargetDate = "2025-09-15",
            Title = "Test Task",
            id = "test-1"
        )

        assertFalse(task.isResolved)
    }

    @Test
    fun `task can be created as resolved`() {
        val resolvedTask = Task(
            Description = "Completed task",
            DueDate = "2025-09-15",
            Priority = 0,
            TargetDate = "2025-09-15",
            Title = "Completed Task",
            id = "completed-1",
            isResolved = true
        )

        assertTrue(resolvedTask.isResolved)
    }

    @Test
    fun `task can be created as unresolved explicitly`() {
        val unresolvedTask = Task(
            Description = "Pending task",
            DueDate = "2025-09-15",
            Priority = 0,
            TargetDate = "2025-09-15",
            Title = "Pending Task",
            id = "pending-1",
            isResolved = false
        )

        assertFalse(unresolvedTask.isResolved)
    }

    @Test
    fun `task equality includes isResolved field`() {
        val task1 = Task(
            Description = "Same task",
            DueDate = "2025-09-15",
            Priority = 0,
            TargetDate = "2025-09-15",
            Title = "Same Task",
            id = "same-id",
            isResolved = true
        )

        val task2 = Task(
            Description = "Same task",
            DueDate = "2025-09-15",
            Priority = 0,
            TargetDate = "2025-09-15",
            Title = "Same Task",
            id = "same-id",
            isResolved = true
        )

        val task3 = Task(
            Description = "Same task",
            DueDate = "2025-09-15",
            Priority = 0,
            TargetDate = "2025-09-15",
            Title = "Same Task",
            id = "same-id",
            isResolved = false
        )

        assertEquals(task1, task2)
        assertNotEquals(task1, task3)
    }

    @Test
    fun `task copy preserves isResolved field`() {
        val originalTask = Task(
            Description = "Original task",
            DueDate = "2025-09-15",
            Priority = 0,
            TargetDate = "2025-09-15",
            Title = "Original Task",
            id = "original-1",
            isResolved = true
        )

        val copiedTask = originalTask.copy(Title = "Updated Task")

        assertEquals("Updated Task", copiedTask.Title)
        assertTrue(copiedTask.isResolved) // Should preserve isResolved
        assertEquals(originalTask.Description, copiedTask.Description)
        assertEquals(originalTask.DueDate, copiedTask.DueDate)
        assertEquals(originalTask.Priority, copiedTask.Priority)
        assertEquals(originalTask.TargetDate, copiedTask.TargetDate)
        assertEquals(originalTask.id, copiedTask.id)
    }
}
