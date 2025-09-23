package com.softwavegames.tasksmanagement.presenter.composeutils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.softwavegames.tasksmanagement.R
import com.softwavegames.tasksmanagement.data.model.Task
import com.softwavegames.tasksmanagement.data.model.TaskStatus
import com.softwavegames.tasksmanagement.data.util.DateUtil
import com.softwavegames.tasksmanagement.ui.theme.Green
import com.softwavegames.tasksmanagement.ui.theme.Red
import com.softwavegames.tasksmanagement.ui.theme.YellowDark

object TaskColorHelper {

    @Composable
    fun rememberTaskColors(task: Task): TaskColors {
        return remember(task.status) {
            val isResolved = task.status == TaskStatus.RESOLVED
            val isUnresolved = task.status == TaskStatus.UNRESOLVED
            val isCantResolve = task.status == TaskStatus.CANT_RESOLVE
            
            TaskColors(
                titleColor = when {
                    isResolved -> Green
                    isUnresolved -> Red
                    isCantResolve -> Red
                    else -> Red
                },
                dateColor = when {
                    isResolved -> Green
                    isUnresolved -> Red
                    isCantResolve -> Red
                    else -> Red
                },
                daysLeftColor = when {
                    isResolved -> Green
                    isUnresolved -> Red
                    isCantResolve -> Red
                    else -> Red
                },
                statusColor = when {
                    isResolved -> Green
                    isUnresolved -> YellowDark
                    isCantResolve -> Red
                    else -> Red
                }
            )
        }
    }

    @Composable
    fun rememberTaskListItemColors(): TaskListItemColors {
        return remember {
            TaskListItemColors(
                titleColor = Red,
                dateColor = Red,
                daysLeftColor = Red
            )
        }
    }

    @Composable
    fun rememberTaskStatusText(task: Task): String {
        return if (task.isResolved) {
            stringResource(R.string.resolved)
        } else {
            stringResource(R.string.unresolved)
        }
    }

    @Composable
    fun rememberTaskDateInfo(task: Task): TaskDateInfo {
        return remember(task.DueDate) {
            TaskDateInfo(
                daysLeft = DateUtil.calculateDaysLeft(task.DueDate),
                formattedDate = DateUtil.formatDate(task.DueDate)
            )
        }
    }
}

data class TaskColors(
    val titleColor: Color,
    val dateColor: Color,
    val daysLeftColor: Color,
    val statusColor: Color
)

/**
 * Data class containing TaskListItem colors (always Red)
 */
data class TaskListItemColors(
    val titleColor: Color,
    val dateColor: Color,
    val daysLeftColor: Color
)

data class TaskDateInfo(
    val daysLeft: Int,
    val formattedDate: String
)
