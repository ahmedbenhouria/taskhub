package com.task.management.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.management.domain.TaskValidationState
import com.task.management.domain.ValidateTaskUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TaskViewModel(
    private val validateTaskUseCase: ValidateTaskUseCase = ValidateTaskUseCase()
): ViewModel() {

    var taskState by mutableStateOf(TaskFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: TaskFormEvent) {
        when (event) {
            is TaskFormEvent.TitleChanged -> {
                taskState = taskState.copy(title = event.title)
            }
            is TaskFormEvent.DescriptionChanged -> {
                taskState = taskState.copy(description = event.description)
            }
            is TaskFormEvent.DueDateChanged -> {
                taskState = taskState.copy(dueDate = event.dueDate)
            }
            is TaskFormEvent.EstimateTaskChanged -> {
                taskState = taskState.copy(estimateTask = event.estimateTime)
            }
            is TaskFormEvent.PriorityChanged -> {
                taskState = taskState.copy(selectedPriority = event.priority)
            }
            is TaskFormEvent.MemberAdded -> {
                taskState.selectedMembers.add(event.member)
            }
            is TaskFormEvent.MemberRemoved -> {
                taskState.selectedMembers.remove(event.member)
            }
            is TaskFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val validationState = validateTaskUseCase.execute(
            taskState.title,
            taskState.description,
            taskState.dueDate,
            taskState.selectedMembers
        )

        viewModelScope.launch {
            validationEventChannel.send(
                if (validationState.isSuccessful)
                    ValidationEvent.Success
                else
                    ValidationEvent.Error(validationState)
            )
        }
    }

    fun resetTaskState() {
        taskState = TaskFormState()
    }

    sealed class ValidationEvent {
        object Success: ValidationEvent()
        data class Error(val validationState: TaskValidationState): ValidationEvent()
    }
}