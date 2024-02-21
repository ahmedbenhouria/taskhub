package com.task.hub.domain.usecase

import com.task.hub.domain.model.TaskValidationState
import com.task.hub.presentation.ui.screen.add.Member
import java.time.LocalDate

class ValidateTaskUseCase {

    fun execute(
        title: String,
        description: String,
        dueDate: LocalDate,
        selectedMembers: MutableList<Member>
    ): TaskValidationState {

        val titleResult = validateTitle(title)
        val descriptionResult = validateDescription(description)
        val dueDateResult = validateDueDate(dueDate)
        val membersResult = validateMembers(selectedMembers)

        val isSuccessful = listOf(
            titleResult,
            descriptionResult,
            dueDateResult,
            membersResult
        ).all { it }

        return TaskValidationState(
            isValidTitle = titleResult,
            isValidDescription = descriptionResult,
            isValidDueDate = dueDateResult,
            isMemberSelected = membersResult,
            isSuccessful = isSuccessful
        )
    }

    private fun validateTitle(title: String): Boolean {
        return title.isNotEmpty()
    }

    private fun validateDescription(description: String): Boolean {
        return description.isNotEmpty() || description.length > 5
    }

    private fun validateDueDate(dueDate: LocalDate): Boolean {
        return !dueDate.isBefore(LocalDate.now())
    }

    private fun validateMembers(members: MutableList<Member>): Boolean {
        return members.isNotEmpty()
    }

}