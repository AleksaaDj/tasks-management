package com.softwavegames.tasksmanagement.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.softwavegames.tasksmanagement.presenter.screens.splash.SplashScreen
import com.softwavegames.tasksmanagement.presenter.screens.tasks.TasksListScreen
import com.softwavegames.tasksmanagement.presenter.screens.taskdetail.TaskDetailsScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SPLASH.route
    ) {
        composable(Screen.SPLASH.route) {
            SplashScreen(
                onNavigateToTasks = {
                    navController.navigate(Screen.TASKS.route) {
                        popUpTo(Screen.SPLASH.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.TASKS.route) {
            TasksListScreen(
                onNavigateToTaskDetails = { taskId ->
                    navController.navigate(Screen.TASK_DETAILS.createRoute(taskId))
                }
            )
        }
        
        composable(Screen.TASK_DETAILS.route) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
            TaskDetailsScreen(
                taskId = taskId,
                onNavigateBack = {
                    navController.popBackStack(Screen.TASKS.route, false)
                }
            )
        }
    }
}
