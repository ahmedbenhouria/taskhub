package com.task.management.presentation.ui.screens.add

import androidx.compose.runtime.mutableStateListOf
import com.task.management.presentation.ui.screens.add.Member
import java.time.LocalDate

data class TaskFormState(
    var title: String = "",
    var description: String = "",
    var dueDate: LocalDate = LocalDate.now(),
    var estimateTask: Int = 3,
    var selectedPriority: String = "Low",
    var selectedMembers: MutableList<Member> = mutableStateListOf()
)