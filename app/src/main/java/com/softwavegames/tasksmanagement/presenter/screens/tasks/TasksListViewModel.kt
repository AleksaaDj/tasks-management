package com.softwavegames.tasksmanagement.presenter.screens.tasks

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwavegames.tasksmanagement.data.TasksRepository
import com.softwavegames.tasksmanagement.model.TasksListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        initializeDatabase()
    }

    private fun initializeDatabase() {
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
                            Log.d("Tasks", "initializeDatabase: ${exception.message}")
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
                                    _uiState.value = _uiState.value.copy(
                                        isLoading = false,
                                        error = "No internet connection and no local data available. Please check your connection and try again."
                                    )
                                }
                            } catch (_: Exception) {
                                _uiState.value = _uiState.value.copy(
                                    isLoading = false,
                                    error = "No internet connection and no local data available. Please check your connection and try again."
                                )
                            }
                        }
                } else {
                    // Database has data, use it directly
                    _uiState.value = _uiState.value.copy(
                        isDatabaseInitialized = true,
                        isLoading = false
                    )
                    loadTasksForSelectedDate()
                }
            } catch (e: Exception) {
                Log.d("Tasks", "initializeDatabase error: ${e.message}")
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
            
            repository.getTasksByDate(dateString)
                .collect { tasks ->
                    _uiState.value = _uiState.value.copy(tasks = tasks)
                }
        }
    }

    fun goToNextDay() {
        val currentDate = _uiState.value.selectedDate
        val nextDate = currentDate.plusDays(1)
        _uiState.value = _uiState.value.copy(selectedDate = nextDate)
        loadTasksForSelectedDate()
    }

    fun goToPreviousDay() {
        val currentDate = _uiState.value.selectedDate
        val previousDate = currentDate.minusDays(1)
        _uiState.value = _uiState.value.copy(selectedDate = previousDate)
        loadTasksForSelectedDate()
    }

    fun refreshTasks() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            repository.initializeDatabase()
                .onSuccess {
                    _uiState.value = _uiState.value.copy(
                        isDatabaseInitialized = true,
                        isLoading = false
                    )
                    loadTasksForSelectedDate()
                }
                .onFailure { exception ->
                    Log.d("Tasks", "refreshTasks: ${exception.message}")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Failed to refresh tasks. Please check your internet connection and try again."
                    )
                }
        }
    }

    fun updateTaskResolvedStatus(localId: String, isResolved: Boolean) {
        viewModelScope.launch {
            repository.updateTaskResolvedStatus(localId, isResolved)
                .onFailure { exception ->
                    Log.d("Tasks", "updateTaskResolvedStatus: ${exception.message}")
                    _uiState.value = _uiState.value.copy(
                        error = exception.message ?: "Failed to update task status"
                    )
                }
        }
    }
}