package com.task.hub.presentation.ui.screen.add

import androidx.compose.runtime.mutableStateListOf
import java.time.LocalDate

data class TaskFormState(
    var title: String = "",
    var description: String = "",
    var dueDate: LocalDate = LocalDate.now(),
    var estimateTask: Int = 3,
    var selectedPriority: String = "Low",
    var selectedMembers: MutableList<Member> = mutableStateListOf()
)