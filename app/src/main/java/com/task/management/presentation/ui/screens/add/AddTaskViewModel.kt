package com.task.management.presentation.ui.screens.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.management.domain.model.TaskValidationState
import com.task.management.domain.usecase.ValidateTaskUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddTaskViewModel(
    private val validateTaskUseCase: ValidateTaskUseCase = ValidateTaskUseCase()
): ViewModel() {

    private var _taskState = MutableStateFlow(TaskFormState())

    val taskState = _taskState.asStateFlow()

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: TaskFormEvent) {
        when (event) {
            is TaskFormEvent.TitleChanged -> {
                _taskState.update { it.copy(title = event.title) }
            }
            is TaskFormEvent.DescriptionChanged -> {
                _taskState.update { it.copy(description = event.description) }
            }
            is TaskFormEvent.DueDateChanged -> {
                _taskState.update { it.copy(dueDate = event.dueDate) }
            }
            is TaskFormEvent.EstimateTaskChanged -> {
                _taskState.update { it.copy(estimateTask = event.estimateTime) }
            }
            is TaskFormEvent.PriorityChanged -> {
                _taskState.update { it.copy(selectedPriority = event.priority) }
            }
            is TaskFormEvent.MemberAdded -> {
                _taskState.value.selectedMembers.add(event.member)
            }
            is TaskFormEvent.MemberRemoved -> {
                _taskState.value.selectedMembers.remove(event.member)
            }
            is TaskFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val validationState = validateTaskUseCase.execute(
            _taskState.value.title,
            _taskState.value.description,
            _taskState.value.dueDate,
            _taskState.value.selectedMembers
        )

        viewModelScope.launch {
            validationEventChannel.send(
                if (validationState.isSuccessful)
                    ValidationEvent.Success
                else
                    ValidationEvent.Error(validationState))
        }
    }

    fun resetTaskState() {
        _taskState.value = TaskFormState()
    }

    sealed class ValidationEvent {
        data object Success: ValidationEvent()
        data class Error(val validationState: TaskValidationState): ValidationEvent()
    }
}