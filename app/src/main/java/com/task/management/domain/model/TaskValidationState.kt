package com.task.management.domain.model


data class TaskValidationState (
    var isValidTitle: Boolean = false,
    var isValidDescription: Boolean = false,
    var isValidDueDate: Boolean = false,
    var isMemberSelected: Boolean = false,
    var isSuccessful:Boolean = false
)