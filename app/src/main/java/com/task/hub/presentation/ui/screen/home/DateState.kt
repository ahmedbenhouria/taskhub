package com.task.hub.presentation.ui.screen.home

import com.task.hub.data.local.Task
import java.time.LocalDate

data class DateState(
    var selectedDate: LocalDate = LocalDate.now(),
    var listTasks: List<Task> = emptyList(),
    var hasTasks: Boolean = true
)