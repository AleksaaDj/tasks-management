package com.softwavegames.tasksmanagement

import org.junit.Assert.*
import org.junit.Test

class StringResourceTest {

    @Test
    fun `string resource constants should be defined`() {
        assertNotNull(R.string.today)
        assertNotNull(R.string.retry)
        assertNotNull(R.string.due_date)
        assertNotNull(R.string.days_left)
        assertNotNull(R.string.app_name)
    }

    @Test
    fun `string resource IDs should be valid`() {
        assertTrue(R.string.today > 0)
        assertTrue(R.string.retry > 0)
        assertTrue(R.string.due_date > 0)
        assertTrue(R.string.days_left > 0)
        assertTrue(R.string.app_name > 0)
    }
}
