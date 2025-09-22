package com.softwavegames.tasksmanagement.presenter.screens.tasks

import com.softwavegames.tasksmanagement.data.TasksRepository
import com.softwavegames.tasksmanagement.data.model.Task
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

@ExperimentalCoroutinesApi
class TasksListViewModelTest {

    @MockK
    lateinit var mockRepository: TasksRepository

    private lateinit var viewModel: TasksListViewModel
    private val testDispatcher = StandardTestDispatcher()

    private val dummyTasks = listOf(
        Task("Desc1", "2024-07-20", 1, "2024-07-20", "Title1", "id1"),
        Task("Desc2", "2024-07-20", 2, "2024-07-20", "Title2", "id2"),
        Task("Desc3", "2024-07-21", 1, "2024-07-21", "Title3", "id3")
    )

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init initializes database and loads tasks if empty`() = runTest {
        coEvery { mockRepository.isDatabaseEmpty() } returns true
        coEvery { mockRepository.initializeDatabase() } returns Result.success(Unit)
        every { mockRepository.getTasksByDate(any()) } returns flowOf(dummyTasks.filter { it.DueDate == LocalDate.now().toString() })

        viewModel = TasksListViewModel(mockRepository)
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value.isDatabaseInitialized)
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `init loads tasks directly if database is not empty`() = runTest {
        coEvery { mockRepository.isDatabaseEmpty() } returns false
        every { mockRepository.getTasksByDate(any()) } returns flowOf(dummyTasks.filter { it.DueDate == LocalDate.now().toString() })

        viewModel = TasksListViewModel(mockRepository)
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value.isDatabaseInitialized)
        assertFalse(viewModel.uiState.value.isLoading)
    }


    @Test
    fun `goToNextDay updates selected date`() = runTest {
        coEvery { mockRepository.isDatabaseEmpty() } returns false
        every { mockRepository.getTasksByDate(any()) } returns flowOf(emptyList())

        viewModel = TasksListViewModel(mockRepository)
        advanceUntilIdle() // Initialize database first

        val initialDate = viewModel.uiState.value.selectedDate
        val nextDate = initialDate.plusDays(1)

        viewModel.goToNextDay()
        advanceUntilIdle()

        assertEquals(nextDate, viewModel.uiState.value.selectedDate)
    }

    @Test
    fun `goToPreviousDay updates selected date`() = runTest {
        coEvery { mockRepository.isDatabaseEmpty() } returns false
        every { mockRepository.getTasksByDate(any()) } returns flowOf(emptyList())

        viewModel = TasksListViewModel(mockRepository)
        advanceUntilIdle() // Initialize database first

        val initialDate = viewModel.uiState.value.selectedDate
        val previousDate = initialDate.minusDays(1)

        viewModel.goToPreviousDay()
        advanceUntilIdle()

        assertEquals(previousDate, viewModel.uiState.value.selectedDate)
    }

    @Test
    fun `refreshTasks forces API call`() = runTest {
        coEvery { mockRepository.isDatabaseEmpty() } returns false
        coEvery { mockRepository.initializeDatabase() } returns Result.success(Unit)
        every { mockRepository.getTasksByDate(any()) } returns flowOf(emptyList())

        viewModel = TasksListViewModel(mockRepository)
        advanceUntilIdle() // Initial setup

        viewModel.refreshTasks()
        advanceUntilIdle()

        assertFalse(viewModel.uiState.value.isLoading)
        assertNull(viewModel.uiState.value.error)
    }

}