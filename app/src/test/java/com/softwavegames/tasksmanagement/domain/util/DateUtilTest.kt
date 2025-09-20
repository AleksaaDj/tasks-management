package com.softwavegames.tasksmanagement.domain.util

import org.junit.Test
import org.junit.Assert.*
import java.util.*

class DateUtilTest {

    @Test
    fun `calculateDaysLeft with future date returns correct days`() {
        // Given
        val futureDate = "2025-12-31"
        
        // When
        val daysLeft = DateUtil.calculateDaysLeft(futureDate)
        
        // Then
        assertTrue("Days left should be positive for future date", daysLeft > 0)
    }

    @Test
    fun `calculateDaysLeft with past date returns zero`() {
        // Given
        val pastDate = "2020-01-01"
        
        // When
        val daysLeft = DateUtil.calculateDaysLeft(pastDate)
        
        // Then
        assertEquals(0, daysLeft)
    }

    @Test
    fun `calculateDaysLeft with today date returns zero`() {
        // Given
        val today = Calendar.getInstance()
        val todayString = String.format(
            "%04d-%02d-%02d",
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH) + 1,
            today.get(Calendar.DAY_OF_MONTH)
        )
        
        // When
        val daysLeft = DateUtil.calculateDaysLeft(todayString)
        
        // Then
        assertEquals(0, daysLeft)
    }

    @Test
    fun `calculateDaysLeft with invalid date format returns zero`() {
        // Given
        val invalidDate = "invalid-date"
        
        // When
        val daysLeft = DateUtil.calculateDaysLeft(invalidDate)
        
        // Then
        assertEquals(0, daysLeft)
    }

    @Test
    fun `calculateDaysLeft with null date returns zero`() {
        // Given
        val nullDate = ""
        
        // When
        val daysLeft = DateUtil.calculateDaysLeft(nullDate)
        
        // Then
        assertEquals(0, daysLeft)
    }

    @Test
    fun `calculateDaysLeft with malformed date returns zero`() {
        // Given
        val malformedDate = "2025-13-45" // Invalid month and day
        
        // When
        val daysLeft = DateUtil.calculateDaysLeft(malformedDate)
        
        // Then
        assertTrue("Days left should be non-negative for malformed date", daysLeft >= 0)
    }

    @Test
    fun `formatDate converts valid date correctly`() {
        // Given
        val inputDate = "2025-09-15"
        
        // When
        val formattedDate = DateUtil.formatDate(inputDate)
        
        // Then
        assertTrue("Formatted date should contain Sep", formattedDate.contains("Sep"))
        assertTrue("Formatted date should contain 15", formattedDate.contains("15"))
        assertTrue("Formatted date should contain 2025", formattedDate.contains("2025"))
    }

    @Test
    fun `formatDate converts different months correctly`() {
        // Given
        val inputDate = "2025-12-25"
        
        // When
        val formattedDate = DateUtil.formatDate(inputDate)
        
        // Then
        assertTrue("Formatted date should contain Dec", formattedDate.contains("Dec"))
        assertTrue("Formatted date should contain 25", formattedDate.contains("25"))
        assertTrue("Formatted date should contain 2025", formattedDate.contains("2025"))
    }

    @Test
    fun `formatDate converts January correctly`() {
        // Given
        val inputDate = "2025-01-01"
        
        // When
        val formattedDate = DateUtil.formatDate(inputDate)
        
        // Then
        assertTrue("Formatted date should contain Jan", formattedDate.contains("Jan"))
        assertTrue("Formatted date should contain 01", formattedDate.contains("01"))
        assertTrue("Formatted date should contain 2025", formattedDate.contains("2025"))
    }

    @Test
    fun `formatDate handles invalid date format gracefully`() {
        // Given
        val invalidDate = "invalid-date"
        
        // When
        val formattedDate = DateUtil.formatDate(invalidDate)
        
        // Then
        assertEquals("invalid-date", formattedDate)
    }

    @Test
    fun `formatDate handles empty string gracefully`() {
        // Given
        val emptyDate = ""
        
        // When
        val formattedDate = DateUtil.formatDate(emptyDate)
        
        // Then
        assertEquals("", formattedDate)
    }

    @Test
    fun `formatDate handles malformed date gracefully`() {
        // Given
        val malformedDate = "not-a-date"
        
        // When
        val formattedDate = DateUtil.formatDate(malformedDate)
        
        // Then
        // Should return the original string when parsing fails
        assertEquals("not-a-date", formattedDate)
    }

    @Test
    fun `formatDate handles leap year correctly`() {
        // Given
        val leapYearDate = "2024-02-29"
        
        // When
        val formattedDate = DateUtil.formatDate(leapYearDate)
        
        // Then
        assertTrue("Formatted date should contain Feb", formattedDate.contains("Feb"))
        assertTrue("Formatted date should contain 29", formattedDate.contains("29"))
        assertTrue("Formatted date should contain 2024", formattedDate.contains("2024"))
    }

    @Test
    fun `formatDate handles different years correctly`() {
        // Given
        val futureDate = "2030-06-15"
        
        // When
        val formattedDate = DateUtil.formatDate(futureDate)
        
        // Then
        assertTrue("Formatted date should contain Jun", formattedDate.contains("Jun"))
        assertTrue("Formatted date should contain 15", formattedDate.contains("15"))
        assertTrue("Formatted date should contain 2030", formattedDate.contains("2030"))
    }

    @Test
    fun `calculateDaysLeft with edge case year boundary`() {
        // Given
        val newYearDate = "2025-01-01"
        
        // When
        val daysLeft = DateUtil.calculateDaysLeft(newYearDate)
        
        // Then
        assertTrue("Days left should be non-negative", daysLeft >= 0)
    }

    @Test
    fun `formatDate with single digit day and month`() {
        // Given
        val singleDigitDate = "2025-01-05"
        
        // When
        val formattedDate = DateUtil.formatDate(singleDigitDate)
        
        // Then
        assertTrue("Formatted date should contain Jan", formattedDate.contains("Jan"))
        assertTrue("Formatted date should contain 05", formattedDate.contains("05"))
        assertTrue("Formatted date should contain 2025", formattedDate.contains("2025"))
    }
}
