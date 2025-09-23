package com.softwavegames.tasksmanagement.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.softwavegames.tasksmanagement.presenter.screens.splash.SplashScreen
import com.softwavegames.tasksmanagement.presenter.screens.tasks.TasksListScreen
import com.softwavegames.tasksmanagement.presenter.screens.taskdetail.TaskDetailsScreen
import com.softwavegames.tasksmanagement.presenter.screens.tasks.TasksListViewModel

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
        
        composable(Screen.TASKS.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Screen.TASKS.route)
            }
            val viewModel = hiltViewModel<TasksListViewModel>(parentEntry, null)
            TasksListScreen(
                viewModel = viewModel,
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
