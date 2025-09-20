package com.softwavegames.tasksmanagement.model

import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDate

class TasksListUiStateTest {

    @Test
    fun `TasksListUiState should have correct default values`() {
        val uiState = TasksListUiState()

        assertTrue(uiState.tasks.isEmpty())
        assertFalse(uiState.isLoading)
        assertNull(uiState.error)
        assertEquals(LocalDate.now(), uiState.selectedDate)
        assertFalse(uiState.isDatabaseInitialized)
    }

    @Test
    fun `TasksListUiState should support custom values`() {
        val tasks = listOf(
            Task(
                id = "1",
                Title = "Test Task",
                Description = "Description",
                DueDate = "2024-01-15",
                TargetDate = "2024-01-15",
                Priority = 1,
                isResolved = false
            )
        )
        val selectedDate = LocalDate.of(2024, 1, 15)
        val error = "Test error"

        val uiState = TasksListUiState(
            tasks = tasks,
            isLoading = true,
            error = error,
            selectedDate = selectedDate,
            isDatabaseInitialized = true
        )

        assertEquals(tasks, uiState.tasks)
        assertTrue(uiState.isLoading)
        assertEquals(error, uiState.error)
        assertEquals(selectedDate, uiState.selectedDate)
        assertTrue(uiState.isDatabaseInitialized)
    }
}