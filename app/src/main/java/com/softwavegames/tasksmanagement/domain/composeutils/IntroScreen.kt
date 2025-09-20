package com.softwavegames.tasksmanagement.domain.composeutils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softwavegames.tasksmanagement.R
import com.softwavegames.tasksmanagement.ui.theme.AmsiProBold
import com.softwavegames.tasksmanagement.ui.theme.TasksManagementTheme
import com.softwavegames.tasksmanagement.ui.theme.Yellow

@Composable
fun IntroScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Yellow)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top section with logo and title
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Logo
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App logo",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(bottom = 32.dp)
                )
                
                // Title "Smart tasks"
                Text(
                    text = "Smart",
                    fontSize = 36.sp,
                    fontFamily = AmsiProBold,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = "tasks",
                    fontSize = 36.sp,
                    fontFamily = AmsiProBold,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            
            // Bottom section with illustrations - no extra padding
            Image(
                painter = painterResource(id = R.drawable.intro_illustration),
                contentDescription = "Intro illustration",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IntroScreenPreview() {
    TasksManagementTheme {
        IntroScreen()
    }
}