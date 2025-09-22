package com.softwavegames.tasksmanagement.presenter.composeutils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softwavegames.tasksmanagement.R
import com.softwavegames.tasksmanagement.data.model.Task
import com.softwavegames.tasksmanagement.data.model.TaskStatus
import com.softwavegames.tasksmanagement.ui.theme.AmsiProRegular
import com.softwavegames.tasksmanagement.ui.theme.Beige
import com.softwavegames.tasksmanagement.ui.theme.TasksManagementTheme

@Composable
fun CommentSection(
    task: Task,
    modifier: Modifier = Modifier
) {
    val hasComment = remember(task.comment) {
        !task.comment.isNullOrBlank()
    }
    
    if (hasComment) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Beige),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.comment),
                    fontSize = 12.sp,
                    fontFamily = AmsiProRegular,
                    color = Color(0xFF666666)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = task.comment ?: "",
                    fontSize = 14.sp,
                    fontFamily = AmsiProRegular,
                    color = Color(0xFF444444),
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommentSectionPreview() {
    TasksManagementTheme {
        CommentSection(
            task = Task(
                Description = "Test description",
                DueDate = "2025-01-01",
                Priority = 1,
                TargetDate = "2025-01-01",
                Title = "Test task",
                id = "test-id",
                isResolved = true,
                status = TaskStatus.RESOLVED,
                comment = "This is a sample comment that shows how the comment section will look when displayed below the task details item. It provides more space and better visual separation."
            )
        )
    }
}

