package com.softwavegames.tasksmanagement.data

import com.softwavegames.tasksmanagement.data.remote.TasksApi
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class TasksRepositoryTest {

    @Mock
    private lateinit var mockTasksApi: TasksApi

    private lateinit var tasksRepository: TasksRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        tasksRepository = TasksRepository(mockTasksApi)
    }

    @Test
    fun `repository should be created with tasks api dependency`() {
        // Given & When
        val repository = TasksRepository(mockTasksApi)

        // Then
        assertNotNull(repository)
    }

    @Test
    fun `repository should accept tasks api in constructor`() {
        // Given
        val customApi = object : TasksApi {}

        // When
        val repository = TasksRepository(customApi)

        // Then
        assertNotNull(repository)
    }

    @Test
    fun `repository should be created successfully`() {
        // Then
        assertNotNull(tasksRepository)
    }
}

