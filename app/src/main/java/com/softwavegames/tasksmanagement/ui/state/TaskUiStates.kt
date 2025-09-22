package com.softwavegames.tasksmanagement.ui.state

import com.softwavegames.tasksmanagement.data.model.Task
import java.time.LocalDate

/**
 * UI state for task details screen
 */
data class TaskDetailsUiState(
    val task: Task? = null,
    override val isLoading: Boolean = false,
    override val error: String? = null
) : BaseUiState

/**
 * UI state for tasks list screen with additional metadata
 */
data class TasksListUiState(
    val tasks: List<Task> = emptyList(),
    val selectedDate: LocalDate = LocalDate.now(),
    val isDatabaseInitialized: Boolean = false,
    override val isLoading: Boolean = false,
    override val error: String? = null
) : BaseUiState

