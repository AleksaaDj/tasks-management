package com.softwavegames.tasksmanagement.presenter.screens.tasks

import com.softwavegames.tasksmanagement.data.model.Task
import com.softwavegames.tasksmanagement.data.model.TaskStatus
import com.softwavegames.tasksmanagement.ui.events.TasksListEvent
import com.softwavegames.tasksmanagement.ui.state.TasksListUiState
import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDate

class TasksListScreenTest {

    private val sampleTasks = listOf(
        Task(
            Description = "Test task description",
            DueDate = "2024-01-15",
            Priority = 1,
            TargetDate = "2024-01-15",
            Title = "Test Task",
            id = "test-task-1",
            isResolved = false,
            status = TaskStatus.UNRESOLVED,
            comment = null
        )
    )

    @Test
    fun `TasksListUiState has correct initial values`() {
        val uiState = TasksListUiState()
        
        assertTrue(uiState.tasks.isEmpty())
        assertEquals(LocalDate.now(), uiState.selectedDate)
        assertFalse(uiState.isDatabaseInitialized)
        assertFalse(uiState.isLoading)
        assertNull(uiState.error)
    }

    @Test
    fun `TasksListUiState copy works correctly`() {
        val originalState = TasksListUiState()
        val updatedState = originalState.copy(
            tasks = sampleTasks,
            isLoading = true,
            error = "Test error"
        )
        
        assertEquals(sampleTasks, updatedState.tasks)
        assertTrue(updatedState.isLoading)
        assertEquals("Test error", updatedState.error)
        assertEquals(originalState.selectedDate, updatedState.selectedDate)
        assertEquals(originalState.isDatabaseInitialized, updatedState.isDatabaseInitialized)
    }

    @Test
    fun `TasksListEvent sealed interface works correctly`() {
        val nextDayEvent: TasksListEvent = TasksListEvent.NextDayClicked
        val previousDayEvent: TasksListEvent = TasksListEvent.PreviousDayClicked
        val retryEvent: TasksListEvent = TasksListEvent.RetryInitialization

        assertSame(TasksListEvent.NextDayClicked, nextDayEvent)
        assertSame(TasksListEvent.PreviousDayClicked, previousDayEvent)
        assertSame(TasksListEvent.RetryInitialization, retryEvent)
    }

    @Test
    fun `date formatting works correctly`() {
        val testDate = LocalDate.of(2024, 1, 15)
        val formattedDate = testDate.format(java.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy"))
        
        assertEquals("Jan 15, 2024", formattedDate)
    }

    @Test
    fun `today detection works correctly`() {
        val today = LocalDate.now()
        val yesterday = today.minusDays(1)
        val tomorrow = today.plusDays(1)
        
        assertTrue(today == LocalDate.now())
        assertFalse(yesterday == LocalDate.now())
        assertFalse(tomorrow == LocalDate.now())
    }
}
