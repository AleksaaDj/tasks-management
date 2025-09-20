package com.softwavegames.tasksmanagement.domain.util

import org.junit.Assert.*
import org.junit.Test

class DateUtilEdgeCasesTest {

    @Test
    fun `calculateDaysLeft with leap year should work correctly`() {
        // Test leap year date (2024 is a leap year)
        val leapYearDate = "2024-02-29"
        val daysLeft = DateUtil.calculateDaysLeft(leapYearDate)
        
        // Should not throw exception and should return a valid number
        assertTrue(daysLeft >= 0)
    }

    @Test
    fun `calculateDaysLeft with year boundary should work correctly`() {
        // Test date at year boundary
        val yearBoundaryDate = "2024-12-31"
        val daysLeft = DateUtil.calculateDaysLeft(yearBoundaryDate)
        
        assertTrue(daysLeft >= 0)
    }

    @Test
    fun `formatDate with leap year should format correctly`() {
        val leapYearDate = "2024-02-29"
        val formattedDate = DateUtil.formatDate(leapYearDate)
        
        assertTrue(formattedDate.contains("Feb 29 2024"))
    }

    @Test
    fun `calculateDaysLeft with very far future date should work`() {
        val farFutureDate = "2030-12-31"
        val daysLeft = DateUtil.calculateDaysLeft(farFutureDate)
        
        assertTrue(daysLeft > 0)
    }

    @Test
    fun `formatDate with year boundary should format correctly`() {
        val yearBoundaryDate = "2024-01-01"
        val formattedDate = DateUtil.formatDate(yearBoundaryDate)
        
        assertTrue(formattedDate.contains("Jan 01 2024"))
    }

    @Test
    fun `calculateDaysLeft with month boundary should work correctly`() {
        val monthBoundaryDate = "2024-03-01"
        val daysLeft = DateUtil.calculateDaysLeft(monthBoundaryDate)
        
        assertTrue(daysLeft >= 0)
    }
}
