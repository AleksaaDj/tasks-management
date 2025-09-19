package com.softwavegames.tasksmanagement.presenter.screens.tasks

import android.content.Context
import androidx.lifecycle.ViewModel
import com.softwavegames.tasksmanagement.data.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class TasksListViewModel @Inject constructor(
    private val repository: TasksRepository,
    @ApplicationContext val context: Context
) :
    ViewModel() {

}