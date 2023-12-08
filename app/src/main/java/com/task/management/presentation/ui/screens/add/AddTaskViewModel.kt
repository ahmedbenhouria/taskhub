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
            if (validationState.isSuccessful) {
                validationEventChannel.send(ValidationEvent.Success)
            } else {
                val errorMessage = getErrorMessage(validationState)
                validationEventChannel.send(ValidationEvent.Error(errorMessage))
            }
        }
    }

    private fun getErrorMessage(validationState: TaskValidationState): String {
        return when {
            !validationState.isValidTitle && !validationState.isValidDescription && !validationState.isMemberSelected ->
                "Fill in the task information correctly."
            !validationState.isValidTitle && !validationState.isValidDescription ->
                "Enter a valid title and description."
            !validationState.isValidTitle && !validationState.isMemberSelected ->
                "Enter a valid title and select at least one member."
            !validationState.isValidTitle -> "Enter a valid title."
            !validationState.isValidDescription -> "Enter a valid description."
            !validationState.isValidDueDate -> "Choose a due date for today or a future date."
            !validationState.isMemberSelected -> "Select at least one member."
            else -> "Fill in the task information correctly."
        }
    }

    fun resetTaskState() {
        _taskState.value = TaskFormState()
    }

    sealed class ValidationEvent {
        data object Success: ValidationEvent()
        data class Error(val errorMessage: String): ValidationEvent()
    }
}