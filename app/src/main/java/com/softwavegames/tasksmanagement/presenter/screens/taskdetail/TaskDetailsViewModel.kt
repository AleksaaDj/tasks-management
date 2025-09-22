package com.softwavegames.tasksmanagement.presenter.screens.taskdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwavegames.tasksmanagement.data.TasksRepository
import com.softwavegames.tasksmanagement.data.model.TaskStatus
import com.softwavegames.tasksmanagement.ui.state.TaskDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    private val repository: TasksRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskDetailsUiState())
    val uiState: StateFlow<TaskDetailsUiState> = _uiState.asStateFlow()

    fun loadTask(taskId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val task = repository.getTaskById(taskId)
                if (task != null) {
                    _uiState.value = _uiState.value.copy(
                        task = task,
                        isLoading = false,
                        error = null
                    )
                } else {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = "Task not found")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load task"
                )
            }
        }
    }

    fun updateTaskStatus(taskId: String, status: TaskStatus) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                repository.updateTaskStatus(taskId, status)
                    .onSuccess {
                        loadTask(taskId)
                    }
                    .onFailure { exception ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = exception.message ?: "Failed to update task status"
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to update task status"
                )
            }
        }
    }

    fun updateTaskStatusWithComment(taskId: String, status: TaskStatus, comment: String?) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                repository.updateTaskStatusWithComment(taskId, status, comment)
                    .onSuccess {
                        loadTask(taskId)
                    }
                    .onFailure { exception ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = exception.message ?: "Failed to update task status"
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to update task status"
                )
            }
        }
    }
}