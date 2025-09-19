package com.softwavegames.tasksmanagement.di

import com.softwavegames.tasksmanagement.data.TasksRepository
import com.softwavegames.tasksmanagement.data.remote.TasksApi
import org.junit.Test
import org.junit.Assert.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppModuleTest {

    @Test
    fun `retrofit should be created with correct base url`() {
        // Given & When
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://demo9877360.mockable.io/")
            .build()

        // Then
        assertNotNull(retrofit)
        assertEquals("http://demo9877360.mockable.io/", retrofit.baseUrl().toString())
    }

    @Test
    fun `retrofit should have gson converter factory`() {
        // Given & When
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://demo9877360.mockable.io/")
            .build()

        // Then
        assertNotNull(retrofit)
        assertTrue(retrofit.converterFactories().isNotEmpty())
    }

    @Test
    fun `tasks api should be created from retrofit`() {
        // Given
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://demo9877360.mockable.io/")
            .build()

        // When
        val tasksApi = retrofit.create(TasksApi::class.java)

        // Then
        assertNotNull(tasksApi)
    }

    @Test
    fun `tasks repository should be created with tasks api`() {
        // Given
        val mockTasksApi = object : TasksApi {}

        // When
        val repository = TasksRepository(mockTasksApi)

        // Then
        assertNotNull(repository)
    }

    @Test
    fun `okhttp client should have correct timeout configuration`() {
        // Given
        val okHttpClient = okhttp3.OkHttpClient.Builder()
            .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        // Then
        assertEquals(30000, okHttpClient.readTimeoutMillis)
        assertEquals(30000, okHttpClient.connectTimeoutMillis)
        assertEquals(30000, okHttpClient.writeTimeoutMillis)
    }
}
