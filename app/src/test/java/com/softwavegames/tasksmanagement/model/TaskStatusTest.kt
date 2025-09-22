package com.softwavegames.tasksmanagement.model

import com.softwavegames.tasksmanagement.data.model.TaskStatus
import org.junit.Test
import org.junit.Assert.*

class TaskStatusTest {

    @Test
    fun `TaskStatus values should be correct`() {
        val values = TaskStatus.entries.toTypedArray()

        assertEquals(3, values.size)
        assertTrue(values.contains(TaskStatus.UNRESOLVED))
        assertTrue(values.contains(TaskStatus.RESOLVED))
        assertTrue(values.contains(TaskStatus.CANT_RESOLVE))
    }

    @Test
    fun `TaskStatus valueOf should work correctly`() {
        assertEquals(TaskStatus.UNRESOLVED, TaskStatus.valueOf("UNRESOLVED"))
        assertEquals(TaskStatus.RESOLVED, TaskStatus.valueOf("RESOLVED"))
        assertEquals(TaskStatus.CANT_RESOLVE, TaskStatus.valueOf("CANT_RESOLVE"))
    }

    @Test
    fun `TaskStatus valueOf should throw exception for invalid value`() {
        val invalidValue = "INVALID_STATUS"

        try {
            TaskStatus.valueOf(invalidValue)
            fail("Expected IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message?.contains(invalidValue) == true)
        }
    }

    @Test
    fun `TaskStatus name should return correct string`() {
        assertEquals("UNRESOLVED", TaskStatus.UNRESOLVED.name)
        assertEquals("RESOLVED", TaskStatus.RESOLVED.name)
        assertEquals("CANT_RESOLVE", TaskStatus.CANT_RESOLVE.name)
    }

    @Test
    fun `TaskStatus ordinal should return correct index`() {
        assertEquals(0, TaskStatus.UNRESOLVED.ordinal)
        assertEquals(1, TaskStatus.RESOLVED.ordinal)
        assertEquals(2, TaskStatus.CANT_RESOLVE.ordinal)
    }

    @Test
    fun `TaskStatus toString should return name`() {
        assertEquals("UNRESOLVED", TaskStatus.UNRESOLVED.toString())
        assertEquals("RESOLVED", TaskStatus.RESOLVED.toString())
        assertEquals("CANT_RESOLVE", TaskStatus.CANT_RESOLVE.toString())
    }
}

