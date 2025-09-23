package com.softwavegames.tasksmanagement.presenter.screens.tasks

import com.softwavegames.tasksmanagement.ui.events.TasksListEvent
import org.junit.Assert.*
import org.junit.Test

class TasksListEventTest {

    @Test
    fun `TasksListEvent sealed interface has correct implementations`() {
        val nextDayEvent: TasksListEvent = TasksListEvent.NextDayClicked
        val previousDayEvent: TasksListEvent = TasksListEvent.PreviousDayClicked
        val retryEvent: TasksListEvent = TasksListEvent.RetryInitialization

        assertSame(TasksListEvent.NextDayClicked, nextDayEvent)
        assertSame(TasksListEvent.PreviousDayClicked, previousDayEvent)
        assertSame(TasksListEvent.RetryInitialization, retryEvent)
    }

    @Test
    fun `TasksListEvent objects have correct string representation`() {
        assertEquals("NextDayClicked", TasksListEvent.NextDayClicked.toString())
        assertEquals("PreviousDayClicked", TasksListEvent.PreviousDayClicked.toString())
        assertEquals("RetryInitialization", TasksListEvent.RetryInitialization.toString())
    }

    @Test
    fun `TasksListEvent objects are equal to themselves`() {
        assertEquals(TasksListEvent.NextDayClicked, TasksListEvent.NextDayClicked)
        assertEquals(TasksListEvent.PreviousDayClicked, TasksListEvent.PreviousDayClicked)
        assertEquals(TasksListEvent.RetryInitialization, TasksListEvent.RetryInitialization)
    }
}
