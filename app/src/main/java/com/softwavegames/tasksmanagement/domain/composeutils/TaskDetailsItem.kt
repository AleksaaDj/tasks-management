package com.softwavegames.tasksmanagement.domain.composeutils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softwavegames.tasksmanagement.R
import com.softwavegames.tasksmanagement.model.Task
import com.softwavegames.tasksmanagement.ui.theme.AmsiProBold
import com.softwavegames.tasksmanagement.ui.theme.AmsiProRegular
import com.softwavegames.tasksmanagement.ui.theme.Green
import com.softwavegames.tasksmanagement.ui.theme.Red
import com.softwavegames.tasksmanagement.ui.theme.TasksManagementTheme
import com.softwavegames.tasksmanagement.ui.theme.YellowDark
import com.softwavegames.tasksmanagement.domain.util.DateUtil

@Composable
fun TaskDetailsItem(
    task: Task,
    modifier: Modifier = Modifier
) {
    val daysLeft = DateUtil.calculateDaysLeft(task.DueDate)

    val titleColor = if (task.isResolved) Green else Red
    val dateColor = if (task.isResolved) Green else Red
    val daysLeftColor = if (task.isResolved) Green else Red
    val statusColor = if (task.isResolved) Green else YellowDark
    val statusText = if (task.isResolved) stringResource(R.string.resolved) else stringResource(R.string.unresolved)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.task_details),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = task.Title,
                fontSize = 24.sp,
                fontFamily = AmsiProBold,
                color = titleColor
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFD0D0D0))
            )
            Spacer(modifier = Modifier.height(7.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = stringResource(R.string.due_date),
                        fontSize = 10.sp,
                        fontFamily = AmsiProRegular,
                        color = Color(0xFF666666)
                    )

                    Text(
                        text = DateUtil.formatDate(task.DueDate),
                        fontSize = 17.sp,
                        fontFamily = AmsiProBold,
                        color = dateColor
                    )
                }

                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = stringResource(R.string.days_left),
                        fontSize = 10.sp,
                        fontFamily = AmsiProRegular,
                        color = Color(0xFF666666)
                    )

                    Text(
                        text = daysLeft.toString(),
                        fontSize = 17.sp,
                        fontFamily = AmsiProBold,
                        color = daysLeftColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFD0D0D0))
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = task.Description,
                fontSize = 13.sp,
                fontFamily = AmsiProRegular,
                color = Color(0xFF444444),
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = statusText,
                fontSize = 17.sp,
                fontFamily = AmsiProBold,
                color = statusColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskDetailsItemPreview() {
    TasksManagementTheme {
        TaskDetailsItem(
            task = Task(
                Description = "Find us some cool place for team building - place reservation for December 5th. We need to organize a fun activity for the mobile team to boost morale and team bonding.",
                DueDate = "2025-09-15",
                Priority = 0,
                TargetDate = "2025-09-15",
                Title = "Task Title",
                id = "a4a044856fca4362a8b72070329c9afd",
                isResolved = false
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TaskDetailsItemResolvedPreview() {
    TasksManagementTheme {
        TaskDetailsItem(
            task = Task(
                Description = "Find us some cool place for team building - place reservation for December 5th. We need to organize a fun activity for the mobile team to boost morale and team bonding.",
                DueDate = "2025-09-15",
                Priority = 0,
                TargetDate = "2025-09-15",
                Title = "Task Title",
                id = "a4a044856fca4362a8b72070329c9afd",
                isResolved = true
            )
        )
    }
}