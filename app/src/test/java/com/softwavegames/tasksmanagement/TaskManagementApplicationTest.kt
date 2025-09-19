package com.softwavegames.tasksmanagement

import org.junit.Test
import org.junit.Assert.*

class TaskManagementApplicationTest {

    @Test
    fun `application should be created successfully`() {
        // Given & When
        val application = TaskManagementApplication()

        // Then
        assertNotNull(application)
        assertTrue(true)
    }

    @Test
    fun `application should have correct class name`() {
        // Given & When
        val application = TaskManagementApplication()

        // Then
        assertEquals("TaskManagementApplication", application.javaClass.simpleName)
    }
}
