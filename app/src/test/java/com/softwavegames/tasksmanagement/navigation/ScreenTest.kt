package com.softwavegames.tasksmanagement.navigation

import org.junit.Test
import org.junit.Assert.*

class ScreenTest {

    @Test
    fun `Screen routes are correct`() {
        assertEquals("splash", Screen.SPLASH.route)
        assertEquals("tasks", Screen.TASKS.route)
        assertEquals("task_details/{taskId}", Screen.TASK_DETAILS.route)
    }

    @Test
    fun `createRoute generates correct task details route`() {
        val taskId = "test-task-123"
        val expectedRoute = "task_details/$taskId"
        val actualRoute = Screen.TASK_DETAILS.createRoute(taskId)
        
        assertEquals(expectedRoute, actualRoute)
    }

    @Test
    fun `createRoute returns original route for non-parameterized screens`() {
        assertEquals("splash", Screen.SPLASH.createRoute("any-id"))
        assertEquals("tasks", Screen.TASKS.createRoute("any-id"))
    }

    @Test
    fun `getTaskIdFromRoute extracts task ID correctly`() {
        val taskId = "test-task-123"
        val route = "task_details/$taskId"
        
        val extractedId = Screen.getTaskIdFromRoute(route)
        
        assertEquals(taskId, extractedId)
    }

    @Test
    fun `getTaskIdFromRoute returns null for invalid routes`() {
        assertNull(Screen.getTaskIdFromRoute("invalid-route"))
        assertNull(Screen.getTaskIdFromRoute("task_details/"))
        assertNull(Screen.getTaskIdFromRoute("task_details"))
    }
}
