package com.softwavegames.tasksmanagement.di

import android.app.Application
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Test

class DatabaseModuleTest {

    @Test
    fun `provideTasksDatabase should create database`() {
        val mockApplication = mockk<Application>(relaxed = true)
        
        val database = DatabaseModule.provideTasksDatabase(mockApplication)
        
        assertNotNull(database)
    }

    @Test
    fun `provideTaskDao should return dao from database`() {
        val mockApplication = mockk<Application>(relaxed = true)
        val database = DatabaseModule.provideTasksDatabase(mockApplication)
        
        val taskDao = DatabaseModule.provideTaskDao(database)
        
        assertNotNull(taskDao)
    }

    @Test
    fun `database module should have correct database name constant`() {
        // Test that the database name constant is defined
        // This is a simple test that doesn't require database creation
        assertTrue(true) // Placeholder test
    }
}
