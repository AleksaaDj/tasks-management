package com.softwavegames.tasksmanagement.navigation

enum class Screen(val route: String) {
    SPLASH("splash"),
    TASKS("tasks"),
    TASK_DETAILS("task_details/{taskId}");
    
    fun createRoute(taskId: String): String {
        return when (this) {
            TASK_DETAILS -> "task_details/$taskId"
            else -> route
        }
    }
    
    companion object {
        fun getTaskIdFromRoute(route: String): String? {
            if (!route.startsWith("task_details/")) return null
            
            val taskId = route.substringAfter("task_details/")
            return taskId.ifEmpty { null }
        }
    }
}
