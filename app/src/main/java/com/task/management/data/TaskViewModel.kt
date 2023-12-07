package com.task.management.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.management.data.local.Task
import com.task.management.data.repositories.TaskRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepositoryImpl
): ViewModel() {

    fun upsertTask(task: Task) {
        viewModelScope.launch {
            repository.upsertTask(task)
        }
    }
}