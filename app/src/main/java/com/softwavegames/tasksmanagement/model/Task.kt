package com.softwavegames.tasksmanagement.model

data class Task(
    val Description: String,
    val DueDate: String,
    val Priority: Int,
    val TargetDate: String,
    val Title: String,
    val id: String
)