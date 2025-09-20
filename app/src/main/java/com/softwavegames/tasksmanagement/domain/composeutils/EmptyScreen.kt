package com.softwavegames.tasksmanagement.domain.composeutils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softwavegames.tasksmanagement.R
import com.softwavegames.tasksmanagement.ui.theme.AmsiProBold
import com.softwavegames.tasksmanagement.ui.theme.Beige
import com.softwavegames.tasksmanagement.ui.theme.TasksManagementTheme
import com.softwavegames.tasksmanagement.ui.theme.Yellow

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
    message: String = stringResource(R.string.no_tasks_for_today)
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.empty_screen),
            contentDescription = "Empty state illustration",
            modifier = Modifier
                .padding(bottom = 39.dp)
        )
        
        Text(
            text = message,
            fontSize = 28.sp,
            fontFamily = AmsiProBold,
            color = Beige
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyScreenPreview() {
    TasksManagementTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Yellow)
        ) {
            EmptyScreen()
        }
    }
}