package com.softwavegames.tasksmanagement.presenter.screens.taskdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.softwavegames.tasksmanagement.R
import com.softwavegames.tasksmanagement.presenter.composeutils.CommentDialog
import com.softwavegames.tasksmanagement.presenter.composeutils.CommentSection
import com.softwavegames.tasksmanagement.presenter.composeutils.TaskDetailsItem
import com.softwavegames.tasksmanagement.data.model.Task
import com.softwavegames.tasksmanagement.data.model.TaskStatus
import com.softwavegames.tasksmanagement.ui.state.TaskDetailsUiState
import com.softwavegames.tasksmanagement.ui.theme.AmsiProBold
import com.softwavegames.tasksmanagement.ui.theme.Green
import com.softwavegames.tasksmanagement.ui.theme.Red
import com.softwavegames.tasksmanagement.ui.theme.TasksManagementTheme
import com.softwavegames.tasksmanagement.ui.theme.Yellow

@Composable
fun TaskDetailsScreen(
    taskId: String,
    onNavigateBack: () -> Unit,
    viewModel: TaskDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    var showCommentDialog by remember { mutableStateOf(false) }
    var pendingStatus by remember { mutableStateOf<TaskStatus?>(null) }

    LaunchedEffect(taskId) {
        viewModel.loadTask(taskId)
    }

    TaskDetailsContent(
        uiState = uiState,
        onNavigateBack = onNavigateBack,
        onResolveTask = {
            pendingStatus = TaskStatus.RESOLVED
            showCommentDialog = true
        },
        onCantResolveTask = {
            pendingStatus = TaskStatus.CANT_RESOLVE
            showCommentDialog = true
        },
        showCommentDialog = showCommentDialog,
        onDismissCommentDialog = {
            showCommentDialog = false
            pendingStatus = null
        },
        onConfirmCommentDialog = { comment ->
            pendingStatus?.let { status ->
                viewModel.updateTaskStatusWithComment(taskId, status, comment)
            }
            showCommentDialog = false
            pendingStatus = null
        },
        onCancelCommentDialog = {
            pendingStatus?.let { status ->
                viewModel.updateTaskStatus(taskId, status)
            }
            showCommentDialog = false
            pendingStatus = null
        }
    )
}

@Composable
private fun TaskDetailsContent(
    uiState: TaskDetailsUiState,
    onNavigateBack: () -> Unit,
    onResolveTask: () -> Unit,
    onCantResolveTask: () -> Unit,
    showCommentDialog: Boolean,
    onDismissCommentDialog: () -> Unit,
    onConfirmCommentDialog: (String?) -> Unit,
    onCancelCommentDialog: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Yellow)
    ) {

        TopBar(
            onNavigateBack = onNavigateBack
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Yellow)
        ) {
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }

                uiState.error != null -> {
                    val errorMessage = remember(uiState.error) {
                        "Error: ${uiState.error}"
                    }
                    
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = errorMessage,
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }

                uiState.task != null -> {
                    uiState.task.let { task ->
                        Column {
                            TaskDetailsItem(
                                task = task,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp)
                            )

                            CommentSection(
                                task = task,
                                modifier = Modifier.padding(top = 10.dp)
                            )

                            val isResolvedOrCantResolve = remember(task.status) {
                                task.status == TaskStatus.RESOLVED || task.status == TaskStatus.CANT_RESOLVE
                            }
                            
                            val statusIconRes = remember(task.status) {
                                if (task.status == TaskStatus.RESOLVED) {
                                    R.drawable.sign_resolved
                                } else {
                                    R.drawable.unresolved_sign
                                }
                            }
                            
                            val statusContentDescription = if (task.status == TaskStatus.RESOLVED) {
                                stringResource(R.string.resolved)
                            } else {
                                stringResource(R.string.unresolved)
                            }
                            
                            if (isResolvedOrCantResolve) {
                                Spacer(modifier = Modifier.height(20.dp))

                                Box(
                                    modifier = Modifier.fillMaxWidth()
                                        .padding(top = 20.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = statusIconRes),
                                        contentDescription = statusContentDescription,
                                        modifier = Modifier.size(120.dp)
                                    )
                                }
                            }

                            val isUnresolved = remember(task.status) {
                                task.status == TaskStatus.UNRESOLVED
                            }
                            
                            if (isUnresolved) {
                                Spacer(modifier = Modifier.height(20.dp))

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Button(
                                        onClick = onResolveTask,
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(8.dp)),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Green
                                        ),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = stringResource(R.string.resolve),
                                            color = Color.White,
                                            fontFamily = AmsiProBold
                                        )
                                    }

                                    Button(
                                        onClick = onCantResolveTask,
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(8.dp)),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Red
                                        ),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = stringResource(R.string.cant_resolve),
                                            color = Color.White,
                                            fontFamily = AmsiProBold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    // Comment Dialog
    CommentDialog(
        isVisible = showCommentDialog,
        onDismiss = onDismissCommentDialog,
        onConfirm = onConfirmCommentDialog,
        onCancel = onCancelCommentDialog
    )
}

@Composable
private fun TopBar(
    onNavigateBack: () -> Unit
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
                onClick = onNavigateBack
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = stringResource(R.string.back),
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                text = stringResource(R.string.task_details),
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = AmsiProBold,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.size(48.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskDetailsScreenWithResolvedTaskPreview() {
    TasksManagementTheme {
        TaskDetailsContent(
            uiState = TaskDetailsUiState(
                task = Task(
                    Description = "Find us some cool place for team building - place reservation for December 5th.",
                    DueDate = "2025-09-15",
                    Priority = 0,
                    TargetDate = "2025-09-15",
                    Title = "Team Building Event Planning",
                    id = "a4a044856fca4362a8b72070329c9afd",
                    isResolved = true,
                    status = TaskStatus.RESOLVED,
                    comment = "Successfully booked the venue at Central Park. All team members confirmed attendance."
                ),
                isLoading = false,
                error = null
            ),
            onNavigateBack = {},
            onResolveTask = {},
            onCantResolveTask = {},
            showCommentDialog = false,
            onDismissCommentDialog = {},
            onConfirmCommentDialog = {},
            onCancelCommentDialog = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TaskDetailsScreenWithUnresolvedTaskPreview() {
    TasksManagementTheme {
        TaskDetailsContent(
            uiState = TaskDetailsUiState(
                task = Task(
                    Description = "Review and update the mobile app's user interface design to improve user experience and accessibility.",
                    DueDate = "2025-10-01",
                    Priority = 1,
                    TargetDate = "2025-09-25",
                    Title = "UI/UX Design Review",
                    id = "b5b155967gdb5473b9c83181440d0bge",
                    isResolved = false,
                    status = TaskStatus.UNRESOLVED,
                    comment = null
                ),
                isLoading = false,
                error = null
            ),
            onNavigateBack = {},
            onResolveTask = {},
            onCantResolveTask = {},
            showCommentDialog = false,
            onDismissCommentDialog = {},
            onConfirmCommentDialog = {},
            onCancelCommentDialog = {}
        )
    }
}