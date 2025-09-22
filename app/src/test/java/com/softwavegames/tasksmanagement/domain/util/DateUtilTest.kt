package com.softwavegames.tasksmanagement.domain.util

import com.softwavegames.tasksmanagement.data.util.DateUtil
import org.junit.Assert.*
import org.junit.Test

class DateUtilTest {

    @Test
    fun `calculateDaysLeft with future date should return positive days`() {
        val futureDate = "2024-12-31"

        val daysLeft = DateUtil.calculateDaysLeft(futureDate)

        assertTrue(daysLeft >= 0)
    }

    @Test
    fun `calculateDaysLeft with past date should return zero`() {
        val pastDate = "2020-01-01"

        val daysLeft = DateUtil.calculateDaysLeft(pastDate)

        assertEquals(0, daysLeft)
    }

    @Test
    fun `calculateDaysLeft with null date should return zero`() {
        val nullDate: String? = null

        val daysLeft = DateUtil.calculateDaysLeft(nullDate)

        assertEquals(0, daysLeft)
    }

    @Test
    fun `calculateDaysLeft with invalid date format should return zero`() {
        val invalidDate = "invalid-date"

        val daysLeft = DateUtil.calculateDaysLeft(invalidDate)

        assertEquals(0, daysLeft)
    }

    @Test
    fun `formatDate with valid date should return formatted date`() {
        val dateString = "2024-01-15"

        val formattedDate = DateUtil.formatDate(dateString)

        assertTrue(formattedDate.contains("Jan"))
        assertTrue(formattedDate.contains("15"))
        assertTrue(formattedDate.contains("2024"))
    }

    @Test
    fun `formatDate with null date should return No date`() {
        val nullDate: String? = null

        val formattedDate = DateUtil.formatDate(nullDate)

        assertEquals("No date", formattedDate)
    }

    @Test
    fun `formatDate with invalid date format should return original string`() {
        val invalidDate = "invalid-date"

        val formattedDate = DateUtil.formatDate(invalidDate)

        assertEquals("invalid-date", formattedDate)
    }
}