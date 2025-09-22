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
class TaskDetailsViewModelCommentTest {

    private lateinit var repository: TasksRepository
    private lateinit var viewModel: TaskDetailsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        repository = mockk()
        viewModel = TaskDetailsViewModel(repository)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `updateTaskStatusWithComment calls repository with comment`() = runTest {
        val taskId = "test-task-id"
        val status = TaskStatus.RESOLVED
        val comment = "Task completed successfully"
        
        coEvery { repository.updateTaskStatusWithComment(taskId, status, comment) } returns Result.success(Unit)
        coEvery { repository.getTaskById(taskId) } returns Task(
            Description = "Test description",
            DueDate = "2025-01-01",
            Priority = 1,
            TargetDate = "2025-01-01",
            Title = "Test task",
            id = taskId,
            isResolved = true,
            status = status,
            comment = comment
        )

        viewModel.updateTaskStatusWithComment(taskId, status, comment)
        advanceUntilIdle()

        coVerify { repository.updateTaskStatusWithComment(taskId, status, comment) }
    }

    @Test
    fun `updateTaskStatusWithComment handles null comment`() = runTest {
        val taskId = "test-task-id"
        val status = TaskStatus.CANT_RESOLVE
        val comment: String? = null
        
        coEvery { repository.updateTaskStatusWithComment(taskId, status, comment) } returns Result.success(Unit)
        coEvery { repository.getTaskById(taskId) } returns Task(
            Description = "Test description",
            DueDate = "2025-01-01",
            Priority = 1,
            TargetDate = "2025-01-01",
            Title = "Test task",
            id = taskId,
            isResolved = false,
            status = status,
            comment = null
        )

        viewModel.updateTaskStatusWithComment(taskId, status, comment)
        advanceUntilIdle()

        coVerify { repository.updateTaskStatusWithComment(taskId, status, comment) }
    }

    @Test
    fun `updateTaskStatusWithComment handles empty comment`() = runTest {
        val taskId = "test-task-id"
        val status = TaskStatus.RESOLVED
        val comment = ""
        
        coEvery { repository.updateTaskStatusWithComment(taskId, status, comment) } returns Result.success(Unit)
        coEvery { repository.getTaskById(taskId) } returns Task(
            Description = "Test description",
            DueDate = "2025-01-01",
            Priority = 1,
            TargetDate = "2025-01-01",
            Title = "Test task",
            id = taskId,
            isResolved = true,
            status = status,
            comment = ""
        )

        viewModel.updateTaskStatusWithComment(taskId, status, comment)
        advanceUntilIdle()

        coVerify { repository.updateTaskStatusWithComment(taskId, status, comment) }
    }

    @Test
    fun `updateTaskStatusWithComment handles repository failure`() = runTest {
        val taskId = "test-task-id"
        val status = TaskStatus.RESOLVED
        val comment = "Test comment"
        val errorMessage = "Database error"
        
        coEvery { repository.updateTaskStatusWithComment(taskId, status, comment) } returns Result.failure(Exception(errorMessage))

        viewModel.updateTaskStatusWithComment(taskId, status, comment)
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertEquals(errorMessage, uiState.error)
    }

    @Test
    fun `updateTaskStatusWithComment handles exception`() = runTest {
        val taskId = "test-task-id"
        val status = TaskStatus.CANT_RESOLVE
        val comment = "Test comment"
        val errorMessage = "Network error"
        
        coEvery { repository.updateTaskStatusWithComment(taskId, status, comment) } throws Exception(errorMessage)

        viewModel.updateTaskStatusWithComment(taskId, status, comment)
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertEquals(errorMessage, uiState.error)
    }
}
