package com.softwavegames.tasksmanagement.presenter.screens.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.softwavegames.tasksmanagement.R
import com.softwavegames.tasksmanagement.presenter.composeutils.EmptyScreen
import com.softwavegames.tasksmanagement.presenter.composeutils.TaskListItem
import com.softwavegames.tasksmanagement.data.model.Task
import com.softwavegames.tasksmanagement.data.model.TaskStatus
import com.softwavegames.tasksmanagement.ui.state.TasksListUiState
import com.softwavegames.tasksmanagement.ui.theme.AmsiProBold
import com.softwavegames.tasksmanagement.ui.theme.Beige
import com.softwavegames.tasksmanagement.ui.theme.Green
import com.softwavegames.tasksmanagement.ui.theme.TasksManagementTheme
import com.softwavegames.tasksmanagement.ui.theme.Yellow
import java.time.LocalDate

@Composable
fun TasksListScreen(
    onNavigateToTaskDetails: (String) -> Unit = {},
    viewModel: TasksListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Refresh tasks when the selected date changes
    LaunchedEffect(uiState.selectedDate) {
        viewModel.refreshCurrentDateTasks()
    }

    TasksListContent(
        uiState = uiState,
        onNavigateToTaskDetails = onNavigateToTaskDetails,
        onPreviousDay = { viewModel.goToPreviousDay() },
        onNextDay = { viewModel.goToNextDay() },
        onRefreshTasks = { viewModel.refreshTasks() }
    )
}

@Composable
private fun TasksListContent(
    uiState: TasksListUiState,
    onNavigateToTaskDetails: (String) -> Unit,
    onPreviousDay: () -> Unit,
    onNextDay: () -> Unit,
    onRefreshTasks: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Yellow)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Yellow)
                .padding(top = 50.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onPreviousDay
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_left),
                        contentDescription = stringResource(R.string.previous_day),
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Text(
                    text = if (uiState.selectedDate == LocalDate.now()) {
                        stringResource(R.string.today)
                    } else {
                        uiState.selectedDate.format(java.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy"))
                    },
                    fontSize = 24.sp,
                    fontFamily = AmsiProBold,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                IconButton(
                    onClick = onNextDay
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = stringResource(R.string.next_day),
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 120.dp)
        ) {
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Beige
                        )
                    }
                }

                uiState.error != null -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Error: ${uiState.error}",
                                color = Color.White,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = onRefreshTasks,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Beige
                                )
                            ) {
                                Text(
                                    text = stringResource(R.string.retry),
                                    color = Green
                                )
                            }
                        }
                    }
                }

                uiState.tasks.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        EmptyScreen()
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(bottom = 26.dp)
                    ) {
                        items(uiState.tasks) { task ->
                            TaskListItem(
                                task = task,
                                onClick = { onNavigateToTaskDetails(task.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TasksListScreenWithTasksPreview() {
    TasksManagementTheme {
        TasksListContent(
            uiState = TasksListUiState(
                tasks = listOf(
                    Task(
                        Description = "Review and update the mobile app's user interface design to improve user experience and accessibility.",
                        DueDate = "2025-10-01",
                        Priority = 1,
                        TargetDate = "2025-09-25",
                        Title = "UI/UX Design Review",
                        id = "task-1",
                        isResolved = false,
                        status = TaskStatus.UNRESOLVED,
                        comment = null
                    ),
                ),
                selectedDate = LocalDate.now(),
                isDatabaseInitialized = true,
                isLoading = false,
                error = null
            ),
            onNavigateToTaskDetails = {},
            onPreviousDay = {},
            onNextDay = {},
            onRefreshTasks = {}
        )
    }
}


@Preview(showBackground = true)
@Composable
fun TasksListScreenErrorPreview() {
    TasksManagementTheme {
        TasksListContent(
            uiState = TasksListUiState(
                tasks = emptyList(),
                selectedDate = LocalDate.now(),
                isDatabaseInitialized = false,
                isLoading = false,
                error = "No internet connection. Please check your network and try again."
            ),
            onNavigateToTaskDetails = {},
            onPreviousDay = {},
            onNextDay = {},
            onRefreshTasks = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TasksListScreenEmptyPreview() {
    TasksManagementTheme {
        TasksListContent(
            uiState = TasksListUiState(
                tasks = emptyList(),
                selectedDate = LocalDate.now(),
                isDatabaseInitialized = true,
                isLoading = false,
                error = null
            ),
            onNavigateToTaskDetails = {},
            onPreviousDay = {},
            onNextDay = {},
            onRefreshTasks = {}
        )
    }
}