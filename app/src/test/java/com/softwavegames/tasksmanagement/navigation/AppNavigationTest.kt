package com.softwavegames.tasksmanagement.navigation

import org.junit.Assert.*
import org.junit.Test

class AppNavigationTest {

    @Test
    fun `navigation routes are properly configured`() {
        val splashRoute = Screen.SPLASH.route
        val tasksRoute = Screen.TASKS.route
        val taskDetailsRoute = Screen.TASK_DETAILS.route

        assertEquals("splash", splashRoute)
        assertEquals("tasks", tasksRoute)
        assertEquals("task_details/{taskId}", taskDetailsRoute)
    }

    @Test
    fun `task details route creation works correctly`() {
        val taskId = "test-task-123"
        val expectedRoute = "task_details/$taskId"
        val actualRoute = Screen.TASK_DETAILS.createRoute(taskId)

        assertEquals(expectedRoute, actualRoute)
    }

    @Test
    fun `task ID extraction from route works correctly`() {
        val taskId = "test-task-456"
        val route = "task_details/$taskId"
        val extractedId = Screen.getTaskIdFromRoute(route)

        assertEquals(taskId, extractedId)
    }
}
