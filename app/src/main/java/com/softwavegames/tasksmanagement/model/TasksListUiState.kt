package com.softwavegames.tasksmanagement.model

import java.time.LocalDate

data class TasksListUiState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedDate: LocalDate = LocalDate.now(),
    val isDatabaseInitialized: Boolean = false
)
