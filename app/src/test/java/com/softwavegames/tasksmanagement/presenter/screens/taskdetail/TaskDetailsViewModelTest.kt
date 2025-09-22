package com.softwavegames.tasksmanagement.presenter.screens.taskdetail

import com.softwavegames.tasksmanagement.data.TasksRepository
import com.softwavegames.tasksmanagement.data.model.Task
import com.softwavegames.tasksmanagement.data.model.TaskStatus
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class TaskDetailsViewModelTest {

    private lateinit var repository: TasksRepository
    private lateinit var viewModel: TaskDetailsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = TaskDetailsViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadTask should update uiState with task when successful`() = runTest {
        val taskId = "test-task-id"
        val task = Task(
            id = taskId,
            Title = "Test Task",
            Description = "Test Description",
            DueDate = "2025-09-21",
            TargetDate = "2025-09-21",
            Priority = 1,
            isResolved = false,
            status = TaskStatus.UNRESOLVED
        )
        
        coEvery { repository.getTaskById(taskId) } returns task

        viewModel.loadTask(taskId)
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertNull(uiState.error)
        assertEquals(task, uiState.task)
    }

    @Test
    fun `loadTask should update uiState with error when repository fails`() = runTest {
        val taskId = "test-task-id"

        coEvery { repository.getTaskById(taskId) } returns null

        viewModel.loadTask(taskId)
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertEquals("Task not found", uiState.error)
        assertNull(uiState.task)
    }

    @Test
    fun `updateTaskStatus should call repository and reload task when successful`() = runTest {
        val taskId = "test-task-id"
        val newStatus = TaskStatus.RESOLVED
        val updatedTask = Task(
            id = taskId,
            Title = "Test Task",
            Description = "Test Description",
            DueDate = "2025-09-21",
            TargetDate = "2025-09-21",
            Priority = 1,
            isResolved = true,
            status = TaskStatus.RESOLVED
        )
        
        coEvery { repository.updateTaskStatus(taskId, newStatus) } returns Result.success(Unit)
        coEvery { repository.getTaskById(taskId) } returns updatedTask

        viewModel.updateTaskStatus(taskId, newStatus)
        advanceUntilIdle()

        coVerify { repository.updateTaskStatus(taskId, newStatus) }
        coVerify { repository.getTaskById(taskId) }
        
        val uiState = viewModel.uiState.value
        assertEquals(updatedTask, uiState.task)
        assertFalse(uiState.isLoading)
        assertNull(uiState.error)
    }

    @Test
    fun `updateTaskStatus should handle repository failure`() = runTest {
        val taskId = "test-task-id"
        val newStatus = TaskStatus.CANT_RESOLVE
        val error = Exception("Database error")
        
        coEvery { repository.updateTaskStatus(taskId, newStatus) } returns Result.failure(error)

        viewModel.updateTaskStatus(taskId, newStatus)
        advanceUntilIdle()

        coVerify { repository.updateTaskStatus(taskId, newStatus) }
        coVerify(exactly = 0) { repository.getTaskById(any()) }
        
        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertEquals("Database error", uiState.error)
    }

    @Test
    fun `updateTaskStatus should handle reload failure`() = runTest {
        val taskId = "test-task-id"
        val newStatus = TaskStatus.RESOLVED
        val reloadError = Exception("Reload failed")
        
        coEvery { repository.updateTaskStatus(taskId, newStatus) } returns Result.success(Unit)
        coEvery { repository.getTaskById(taskId) } throws reloadError

        viewModel.updateTaskStatus(taskId, newStatus)
        advanceUntilIdle()

        coVerify { repository.updateTaskStatus(taskId, newStatus) }
        coVerify { repository.getTaskById(taskId) }
        
        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertEquals("Reload failed", uiState.error)
    }

    @Test
    fun `initial uiState should have correct default values`() {
        val uiState = viewModel.uiState.value

        assertFalse(uiState.isLoading)
        assertNull(uiState.error)
        assertNull(uiState.task)
    }

    @Test
    fun `updateTaskStatus should work with CANT_RESOLVE status`() = runTest {
        val taskId = "test-task-id"
        val newStatus = TaskStatus.CANT_RESOLVE
        val updatedTask = Task(
            id = taskId,
            Title = "Test Task",
            Description = "Test Description",
            DueDate = "2025-09-21",
            TargetDate = "2025-09-21",
            Priority = 1,
            isResolved = true,
            status = TaskStatus.CANT_RESOLVE
        )
        
        coEvery { repository.updateTaskStatus(taskId, newStatus) } returns Result.success(Unit)
        coEvery { repository.getTaskById(taskId) } returns updatedTask

        viewModel.updateTaskStatus(taskId, newStatus)
        advanceUntilIdle()

        coVerify { repository.updateTaskStatus(taskId, newStatus) }
        coVerify { repository.getTaskById(taskId) }
        
        val uiState = viewModel.uiState.value
        assertEquals(updatedTask, uiState.task)
        assertEquals(TaskStatus.CANT_RESOLVE, uiState.task?.status)
    }
}
