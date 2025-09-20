package com.softwavegames.tasksmanagement.domain.composeutils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softwavegames.tasksmanagement.R
import com.softwavegames.tasksmanagement.model.Task
import com.softwavegames.tasksmanagement.ui.theme.AmsiProBold
import com.softwavegames.tasksmanagement.ui.theme.AmsiProRegular
import com.softwavegames.tasksmanagement.ui.theme.Beige
import com.softwavegames.tasksmanagement.ui.theme.Red
import com.softwavegames.tasksmanagement.ui.theme.TasksManagementTheme
import com.softwavegames.tasksmanagement.domain.util.DateUtil

@Composable
fun TaskListItem(
    task: Task,
    modifier: Modifier = Modifier
) {
    val daysLeft = DateUtil.calculateDaysLeft(task.DueDate)
    
    Card(
        modifier = modifier
            .width(298.dp)
            .heightIn(min = 80.dp)
            .background(
                color = Beige,
                shape = RoundedCornerShape(5.dp)
            ),
        shape = RoundedCornerShape(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = task.Title,
                fontSize = 15.sp,
                fontFamily = AmsiProBold,
                color = Red
            )
            
            Spacer(modifier = Modifier.height(7.dp))
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
                    verticalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    Text(
                        text = stringResource(R.string.due_date),
                        fontSize = 10.sp,
                        fontFamily = AmsiProRegular,
                        color = Color(0xFF666666)
                    )

                    Text(
                        text = DateUtil.formatDate(task.DueDate),
                        fontSize = 15.sp,
                        fontFamily = AmsiProBold,
                        color = Red
                    )
                }
                
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    Text(
                        text = stringResource(R.string.days_left),
                        fontSize = 10.sp,
                        fontFamily = AmsiProRegular,
                        color = Color(0xFF666666)
                    )

                    Text(
                        text = daysLeft.toString(),
                        fontSize = 15.sp,
                        fontFamily = AmsiProBold,
                        color = Red
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskListItemPreview() {
    TasksManagementTheme {
        TaskListItem(
            task = Task(
                Description = "Find us some cool place for team building - place reservation for December 5th",
                DueDate = "2025-09-15",
                Priority = 0,
                TargetDate = "2025-09-15",
                Title = "Organize team building event",
                id = "a4a044856fca4362a8b72070329c9afd"
            )
        )
    }
}