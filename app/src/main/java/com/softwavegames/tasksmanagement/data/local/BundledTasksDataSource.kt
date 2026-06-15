package com.softwavegames.tasksmanagement.data.local

import android.content.Context
import com.google.gson.Gson
import com.softwavegames.tasksmanagement.data.model.TasksResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BundledTasksDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun load(): TasksResponse {
        context.assets.open(ASSET_FILE).bufferedReader().use { reader ->
            return Gson().fromJson(reader, TasksResponse::class.java)
        }
    }

    companion object {
        private const val ASSET_FILE = "sample_tasks.json"
    }
}
