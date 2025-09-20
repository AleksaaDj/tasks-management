package com.softwavegames.tasksmanagement.presenter.screens.splash

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.softwavegames.tasksmanagement.presenter.screens.intro.IntroScreen
import com.softwavegames.tasksmanagement.presenter.screens.tasks.TasksListScreen
import com.softwavegames.tasksmanagement.ui.theme.TasksManagementTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {
    var showIntro by remember { mutableStateOf(true) }
    
    LaunchedEffect(Unit) {
        delay(2000)
        showIntro = false
    }
    
    if (showIntro) {
        IntroScreen(modifier = Modifier.fillMaxSize())
    } else {
        TasksListScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    TasksManagementTheme {
        SplashScreen()
    }
}
