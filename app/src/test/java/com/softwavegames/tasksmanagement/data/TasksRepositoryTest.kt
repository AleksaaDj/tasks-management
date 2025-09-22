package com.softwavegames.tasksmanagement.data

import com.softwavegames.tasksmanagement.data.local.TaskDao
import com.softwavegames.tasksmanagement.data.local.TaskEntity
import com.softwavegames.tasksmanagement.data.remote.TasksApi
import com.softwavegames.tasksmanagement.data.model.TasksResponse
import com.softwavegames.tasksmanagement.data.model.Task
import com.softwavegames.tasksmanagement.data.model.TaskStatus
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

    @Test
    fun `getTaskById returns task when found in DAO`() = runTest {
        val taskId = "apiId1"
        val taskEntity = dummyTaskEntities.first()
        coEvery { mockTaskDao.getTaskById(taskId) } returns taskEntity

        val result = repository.getTaskById(taskId)

        assertNotNull(result)
        assertEquals(taskEntity.apiId, result?.id)
        assertEquals(taskEntity.title, result?.Title)
        coVerify(exactly = 1) { mockTaskDao.getTaskById(taskId) }
    }

    @Test
    fun `getTaskById returns null when not found in DAO`() = runTest {
        val taskId = "nonExistentId"
        coEvery { mockTaskDao.getTaskById(taskId) } returns null

        val result = repository.getTaskById(taskId)

        assertNull(result)
        coVerify(exactly = 1) { mockTaskDao.getTaskById(taskId) }
    }

    @Test
    fun `updateTaskStatus with RESOLVED status calls DAO with correct parameters`() = runTest {
        val taskId = "apiId1"
        val status = TaskStatus.RESOLVED
        coEvery { mockTaskDao.updateTaskStatus(taskId, status.name, true) } returns Unit

        val result = repository.updateTaskStatus(taskId, status)

        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { mockTaskDao.updateTaskStatus(taskId, status.name, true) }
    }

    @Test
    fun `updateTaskStatus with UNRESOLVED status calls DAO with correct parameters`() = runTest {
        val taskId = "apiId1"
        val status = TaskStatus.UNRESOLVED
        coEvery { mockTaskDao.updateTaskStatus(taskId, status.name, false) } returns Unit

        val result = repository.updateTaskStatus(taskId, status)

        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { mockTaskDao.updateTaskStatus(taskId, status.name, false) }
    }

    @Test
    fun `updateTaskStatus with CANT_RESOLVE status calls DAO with correct parameters`() = runTest {
        val taskId = "apiId1"
        val status = TaskStatus.CANT_RESOLVE
        coEvery { mockTaskDao.updateTaskStatus(taskId, status.name, false) } returns Unit

        val result = repository.updateTaskStatus(taskId, status)

        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { mockTaskDao.updateTaskStatus(taskId, status.name, false) }
    }

    @Test
    fun `updateTaskStatus handles DAO failure`() = runTest {
        val taskId = "apiId1"
        val status = TaskStatus.RESOLVED
        val exception = Exception("DAO error")
        coEvery { mockTaskDao.updateTaskStatus(taskId, status.name, true) } throws exception

        val result = repository.updateTaskStatus(taskId, status)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify(exactly = 1) { mockTaskDao.updateTaskStatus(taskId, status.name, true) }
    }

    @Test
    fun `updateTaskStatusWithComment with RESOLVED status and comment calls DAO with correct parameters`() = runTest {
        val taskId = "apiId1"
        val status = TaskStatus.RESOLVED
        val comment = "Task completed successfully"
        coEvery { mockTaskDao.updateTaskStatusWithComment(taskId, status.name, true, comment) } returns Unit

        val result = repository.updateTaskStatusWithComment(taskId, status, comment)

        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { mockTaskDao.updateTaskStatusWithComment(taskId, status.name, true, comment) }
    }

    @Test
    fun `updateTaskStatusWithComment with UNRESOLVED status and null comment calls DAO with correct parameters`() = runTest {
        val taskId = "apiId1"
        val status = TaskStatus.UNRESOLVED
        val comment: String? = null
        coEvery { mockTaskDao.updateTaskStatusWithComment(taskId, status.name, false, comment) } returns Unit

        val result = repository.updateTaskStatusWithComment(taskId, status, comment)

        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { mockTaskDao.updateTaskStatusWithComment(taskId, status.name, false, comment) }
    }

    @Test
    fun `updateTaskStatusWithComment handles DAO failure`() = runTest {
        val taskId = "apiId1"
        val status = TaskStatus.RESOLVED
        val comment = "Task completed"
        val exception = Exception("DAO error")
        coEvery { mockTaskDao.updateTaskStatusWithComment(taskId, status.name, true, comment) } throws exception

        val result = repository.updateTaskStatusWithComment(taskId, status, comment)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify(exactly = 1) { mockTaskDao.updateTaskStatusWithComment(taskId, status.name, true, comment) }
    }

    @Test
    fun `initializeDatabase handles task mapping failures gracefully`() = runTest {
        // Create tasks with invalid data that will cause mapping to fail
        val invalidTasks = listOf(
            Task("Valid Task", "2024-07-20", 1, "2024-07-20", "Valid Title", "validId"),
            Task("", "2024-07-20", 1, "2024-07-20", "", "invalidId1"), // Empty title and description
            Task("Valid Task 2", "2024-07-21", 1, "2024-07-21", "Valid Title 2", "validId2")
        )
        coEvery { mockTasksApi.getTasks() } returns TasksResponse(invalidTasks)
        coEvery { mockTaskDao.insertTasks(any()) } returns Unit

        val result = repository.initializeDatabase()

        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { mockTasksApi.getTasks() }
        coVerify(exactly = 1) { mockTaskDao.insertTasks(any()) }
    }

    @Test
    fun `initializeDatabase with empty API response still succeeds`() = runTest {
        coEvery { mockTasksApi.getTasks() } returns TasksResponse(emptyList())
        coEvery { mockTaskDao.insertTasks(emptyList()) } returns Unit

        val result = repository.initializeDatabase()

        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { mockTasksApi.getTasks() }
        coVerify(exactly = 1) { mockTaskDao.insertTasks(emptyList()) }
    }
}