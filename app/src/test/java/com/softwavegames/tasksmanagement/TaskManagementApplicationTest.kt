package com.softwavegames.tasksmanagement

import org.junit.Test
import org.junit.Assert.*

class TaskManagementApplicationTest {

    @Test
    fun `application should be created successfully`() {
        val application = TaskManagementApplication()

        assertNotNull(application)
        assertTrue(true)
    }

    @Test
    fun `application should have correct class name`() {
        val application = TaskManagementApplication()

        assertEquals("TaskManagementApplication", application.javaClass.simpleName)
    }
}
