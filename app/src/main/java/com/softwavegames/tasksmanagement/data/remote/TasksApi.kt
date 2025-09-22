package com.softwavegames.tasksmanagement.data.remote

import com.softwavegames.tasksmanagement.data.model.TasksResponse
import retrofit2.http.GET

interface TasksApi {
    @GET(".")
    suspend fun getTasks(): TasksResponse
}