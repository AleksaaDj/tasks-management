package com.softwavegames.tasksmanagement.ui.state

/**
 * Base UI state interface that all UI states should implement
 * Provides common loading, error, and success states
 */
interface BaseUiState {
    val isLoading: Boolean
    val error: String?
}


