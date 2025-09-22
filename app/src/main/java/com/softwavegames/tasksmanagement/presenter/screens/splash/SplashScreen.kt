package com.softwavegames.tasksmanagement.presenter.screens.splash

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.softwavegames.tasksmanagement.presenter.screens.intro.IntroScreen
import com.softwavegames.tasksmanagement.ui.theme.TasksManagementTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToTasks: () -> Unit = {}
) {
    var showIntro by remember { mutableStateOf(true) }
    
    LaunchedEffect(Unit) {
        delay(2000)
        showIntro = false
        onNavigateToTasks()
    }
    
    if (showIntro) {
        IntroScreen(modifier = Modifier.fillMaxSize())
    } else {
        // This will be handled by navigation
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    TasksManagementTheme {
        SplashScreen()
    }
}
