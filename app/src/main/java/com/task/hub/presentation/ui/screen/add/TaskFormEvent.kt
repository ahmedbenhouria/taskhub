package com.task.hub.presentation.ui.screen.add

import java.time.LocalDate

sealed interface TaskFormEvent {
    data class TitleChanged(val title: String): TaskFormEvent
    data class DescriptionChanged(val description: String): TaskFormEvent
    data class DueDateChanged(val dueDate: LocalDate): TaskFormEvent
    data class EstimateTaskChanged(val estimateTime: Int): TaskFormEvent
    data class PriorityChanged(val priority: String): TaskFormEvent
    data class MemberAdded(val member: Member): TaskFormEvent
    data class MemberRemoved(val member: Member): TaskFormEvent

    data object Submit: TaskFormEvent
}