package com.softwavegames.tasksmanagement.ui.events

sealed interface TasksListEvent {
    data object NextDayClicked : TasksListEvent
    data object PreviousDayClicked : TasksListEvent
    data object RetryInitialization : TasksListEvent
}