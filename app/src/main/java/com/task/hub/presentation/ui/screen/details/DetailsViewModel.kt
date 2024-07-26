package com.task.hub.presentation.ui.screen.details

import androidx.lifecycle.SavedStateHandle
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
class DetailsViewModel @Inject constructor(
    private val repository: TaskRepositoryImpl,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private var _taskDetails = MutableStateFlow(Task())
    val taskDetails = _taskDetails.asStateFlow()

    init {
        savedStateHandle.get<Int>("taskId")?.let { id ->
            viewModelScope.launch {
                _taskDetails.value = repository.getTaskById(id)
            }
        }
    }
}

