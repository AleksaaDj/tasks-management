package com.softwavegames.tasksmanagement.data

import com.softwavegames.tasksmanagement.data.local.TaskDao
import com.softwavegames.tasksmanagement.data.local.TaskEntity
import com.softwavegames.tasksmanagement.data.remote.TasksApi
import com.softwavegames.tasksmanagement.model.TasksResponse
import com.softwavegames.tasksmanagement.model.Task
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.UUID

class TasksRepositoryTest {

    @MockK
    lateinit var mockTasksApi: TasksApi

    @MockK
    lateinit var mockTaskDao: TaskDao

    private lateinit var repository: TasksRepository

    private val dummyApiTasks = listOf(
        Task("Desc1", "2024-07-20", 1, "2024-07-20", "Title1", "apiId1"),
        Task("Desc2", "2024-07-20", 2, "2024-07-20", "Title2", "apiId2"),
        Task("Desc3", "2024-07-21", 1, "2024-07-21", "Title3", "apiId3")
    )

    private val dummyTaskEntities = dummyApiTasks.map {
        TaskEntity(
            localId = UUID.randomUUID().toString(),
            apiId = it.id,
            title = it.Title,
            description = it.Description,
            dueDate = it.DueDate,
            targetDate = it.TargetDate,
            priority = it.Priority,
            isResolved = false
        )
    }

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = TasksRepository(mockTasksApi, mockTaskDao)
    }

    @Test
    fun `initializeDatabase fetches from API and inserts into DAO`() = runTest {
        coEvery { mockTasksApi.getTasks() } returns TasksResponse(dummyApiTasks)
        coEvery { mockTaskDao.insertTasks(any()) } returns Unit

        val result = repository.initializeDatabase()

        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { mockTasksApi.getTasks() }
        coVerify(exactly = 1) { mockTaskDao.insertTasks(any()) }
    }

    @Test
    fun `initializeDatabase handles API failure during initialization`() = runTest {
        val exception = Exception("API init error")
        coEvery { mockTasksApi.getTasks() } throws exception

        val result = repository.initializeDatabase()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify(exactly = 1) { mockTasksApi.getTasks() }
        coVerify(exactly = 0) { mockTaskDao.insertTasks(any()) }
    }

    @Suppress("UnusedFlow")
    @Test
    fun `getTasksByDate retrieves filtered tasks from DAO`() = runTest {
        val date = "2024-07-20"
        val filteredEntities = dummyTaskEntities.filter { it.dueDate == date }
        val mockFlow = flow { emit(filteredEntities) }
        every { mockTaskDao.getTasksByDate(date) } returns mockFlow

        val tasksFlow = repository.getTasksByDate(date)
        val collectedTasks = mutableListOf<Task>()
        tasksFlow.collect { tasks ->
            collectedTasks.addAll(tasks)
        }
        
        assertEquals(filteredEntities.size, collectedTasks.size)
        assertTrue(collectedTasks.all { it.DueDate == date })
        verify(exactly = 1) { mockTaskDao.getTasksByDate(date) }
    }

    @Test
    fun `updateTaskResolvedStatus updates task in DAO`() = runTest {
        val localId = dummyTaskEntities[0].localId
        val isResolved = true
        coEvery { mockTaskDao.updateTaskResolvedStatus(localId, isResolved) } returns Unit

        val result = repository.updateTaskResolvedStatus(localId, isResolved)

        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { mockTaskDao.updateTaskResolvedStatus(localId, isResolved) }
    }

    @Test
    fun `isDatabaseEmpty returns true if DAO count is zero`() = runTest {
        coEvery { mockTaskDao.getTaskCount() } returns 0

        val isEmpty = repository.isDatabaseEmpty()

        assertTrue(isEmpty)
        coVerify(exactly = 1) { mockTaskDao.getTaskCount() }
    }

    @Test
    fun `isDatabaseEmpty returns false if DAO count is greater than zero`() = runTest {
        coEvery { mockTaskDao.getTaskCount() } returns 5

        val isEmpty = repository.isDatabaseEmpty()

        assertFalse(isEmpty)
        coVerify(exactly = 1) { mockTaskDao.getTaskCount() }
    }
}