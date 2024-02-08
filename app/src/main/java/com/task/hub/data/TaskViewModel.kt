package com.task.hub.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.hub.data.local.Task
import com.task.hub.data.repositories.TaskRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepositoryImpl
): ViewModel() {

    private val _getAllTasks = MutableStateFlow(emptyList<Task>())
    val getAllTasks = _getAllTasks.asStateFlow()

    init {
        viewModelScope.launch {
            _getAllTasks.emit(repository.getAllTasks())
        }
    }

    fun insertTask(task: Task) {
        viewModelScope.launch {
            repository.insertTask(task)
        }
    }
}