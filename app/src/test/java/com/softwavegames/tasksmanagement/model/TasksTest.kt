package com.softwavegames.tasksmanagement.model

import com.softwavegames.tasksmanagement.data.model.Task
import com.softwavegames.tasksmanagement.data.model.TasksResponse
import org.junit.Test
import org.junit.Assert.*

class TasksResponseTest {

    @Test
    fun `tasks creation with empty list should work correctly`() {
        val tasks = TasksResponse(tasks = emptyList())

        assertNotNull(tasks.tasks)
        assertTrue(tasks.tasks.isEmpty())
    }

    @Test
    fun `tasks creation with multiple tasks should work correctly`() {
        val task1 = Task(
            Description = "First task",
            DueDate = "2025-09-15",
            Priority = 0,
            TargetDate = "2025-09-15",
            Title = "Task 1",
            id = "task-1"
        )

        val task2 = Task(
            Description = "Second task",
            DueDate = "2025-09-16",
            Priority = 1,
            TargetDate = "2025-09-16",
            Title = "Task 2",
            id = "task-2"
        )

        val tasks = TasksResponse(tasks = listOf(task1, task2))

        assertEquals(2, tasks.tasks.size)
        assertEquals(task1, tasks.tasks[0])
        assertEquals(task2, tasks.tasks[1])
    }

    @Test
    fun `tasks equality should work correctly`() {
        val task1 = Task(
            Description = "Test task",
            DueDate = "2025-09-15",
            Priority = 0,
            TargetDate = "2025-09-15",
            Title = "Test Task",
            id = "test-1"
        )

        val tasks1 = TasksResponse(tasks = listOf(task1))
        val tasks2 = TasksResponse(tasks = listOf(task1))
        val tasks3 = TasksResponse(tasks = emptyList())

        assertEquals(tasks1, tasks2)
        assertNotEquals(tasks1, tasks3)
        assertEquals(tasks1.hashCode(), tasks2.hashCode())
    }

    @Test
    fun `tasks with different order should not be equal`() {
        val task1 = Task(
            Description = "First task",
            DueDate = "2025-09-15",
            Priority = 0,
            TargetDate = "2025-09-15",
            Title = "Task 1",
            id = "task-1"
        )

        val task2 = Task(
            Description = "Second task",
            DueDate = "2025-09-16",
            Priority = 1,
            TargetDate = "2025-09-16",
            Title = "Task 2",
            id = "task-2"
        )

        val tasks1 = TasksResponse(tasks = listOf(task1, task2))
        val tasks2 = TasksResponse(tasks = listOf(task2, task1))

        assertNotEquals(tasks1, tasks2)
    }
}
