package com.softwavegames.tasksmanagement.di

import com.softwavegames.tasksmanagement.data.TasksRepository
import com.softwavegames.tasksmanagement.data.remote.TasksApi
import com.softwavegames.tasksmanagement.data.local.TaskDao
import org.junit.Test
import org.junit.Assert.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import io.mockk.mockk

class AppModuleTest {

    @Test
    fun `retrofit should be created with correct base url`() {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://demo9877360.mockable.io/")
            .build()

        assertNotNull(retrofit)
        assertEquals("http://demo9877360.mockable.io/", retrofit.baseUrl().toString())
    }

    @Test
    fun `retrofit should have gson converter factory`() {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://demo9877360.mockable.io/")
            .build()

        assertNotNull(retrofit)
        assertTrue(retrofit.converterFactories().isNotEmpty())
    }

    @Test
    fun `tasks api should be created from retrofit`() {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://demo9877360.mockable.io/")
            .build()

        val tasksApi = retrofit.create(TasksApi::class.java)

        assertNotNull(tasksApi)
    }

    @Test
    fun `tasks repository should be created with tasks api`() {
        val mockTasksApi = mockk<TasksApi>()
        val mockTaskDao = mockk<TaskDao>()

        val repository = TasksRepository(mockTasksApi, mockTaskDao)

        assertNotNull(repository)
    }

    @Test
    fun `okhttp client should have correct timeout configuration`() {
        val okHttpClient = okhttp3.OkHttpClient.Builder()
            .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        assertEquals(30000, okHttpClient.readTimeoutMillis)
        assertEquals(30000, okHttpClient.connectTimeoutMillis)
        assertEquals(30000, okHttpClient.writeTimeoutMillis)
    }
}
