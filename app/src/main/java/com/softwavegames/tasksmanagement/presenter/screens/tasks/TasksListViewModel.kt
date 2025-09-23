package com.softwavegames.tasksmanagement.presenter.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwavegames.tasksmanagement.data.TasksRepository
import com.softwavegames.tasksmanagement.ui.events.TasksListEvent
import com.softwavegames.tasksmanagement.ui.state.TasksListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class TasksListViewModel @Inject constructor(
    private val repository: TasksRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TasksListUiState())
    val uiState: StateFlow<TasksListUiState> = _uiState.asStateFlow()

    init {
        onEvent(TasksListEvent.RetryInitialization)
    }

    fun onEvent(event: TasksListEvent) {
        when (event) {
            TasksListEvent.NextDayClicked -> goToNextDay()
            TasksListEvent.PreviousDayClicked -> goToPreviousDay()
            TasksListEvent.RetryInitialization -> refreshTasks()
        }
    }

    private fun refreshTasks() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val isEmpty = repository.isDatabaseEmpty()

                if (isEmpty) {
                    // Only fetch from API if database is empty
                    repository.initializeDatabase()
                        .onSuccess {
                            _uiState.value = _uiState.value.copy(
                                isDatabaseInitialized = true,
                                isLoading = false
                            )
                            loadTasksForSelectedDate()
                        }
                        .onFailure { exception ->
                            // Try to use any existing local data as fallback
                            try {
                                val hasLocalData = !repository.isDatabaseEmpty()

                                if (hasLocalData) {
                                    _uiState.value = _uiState.value.copy(
                                        isDatabaseInitialized = true,
                                        isLoading = false
                                    )
                                    loadTasksForSelectedDate()
                                } else {
                                    val errorMessage = when {
                                        exception.message?.contains("Unable to resolve host") == true ->
                                            "No internet connection. Please check your network and try again."
                                        exception.message?.contains("timeout") == true ->
                                            "Connection timeout. Please check your internet connection and try again."
                                        else ->
                                            "Failed to load tasks. Please check your internet connection and try again."
                                    }
                                    _uiState.value = _uiState.value.copy(
                                        isLoading = false,
                                        error = errorMessage
                                    )
                                }
                            } catch (_: Exception) {
                                _uiState.value = _uiState.value.copy(
                                    isLoading = false,
                                    error = "Failed to load tasks. Please check your internet connection and try again."
                                )
                            }
                        }
                } else {
                    _uiState.value = _uiState.value.copy(
                        isDatabaseInitialized = true,
                        isLoading = false
                    )
                    loadTasksForSelectedDate()
                }
            } catch (_: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to load tasks. Please check your internet connection and try again."
                )
            }
        }
    }

    private fun loadTasksForSelectedDate() {
        viewModelScope.launch {
            val selectedDate = _uiState.value.selectedDate
            val dateString = selectedDate.format(DateTimeFormatter.ISO_LOCAL_DATE)

            try {
                val tasks = repository.getTasksByDate(dateString).first()
                _uiState.value = _uiState.value.copy(tasks = tasks)
            } catch (_: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to load tasks for the selected date."
                )
            }
        }
    }

    fun refreshCurrentDateTasks() {
        loadTasksForSelectedDate()
    }

    private fun goToNextDay() {
        val nextDay = _uiState.value.selectedDate.plusDays(1)
        _uiState.value = _uiState.value.copy(selectedDate = nextDay)
    }

    private fun goToPreviousDay() {
        val previousDay = _uiState.value.selectedDate.minusDays(1)
        _uiState.value = _uiState.value.copy(selectedDate = previousDay)
    }
}