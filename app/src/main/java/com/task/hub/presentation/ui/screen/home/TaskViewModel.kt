package com.task.hub.presentation.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.hub.data.local.Task
import com.task.hub.data.repositories.TaskRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TaskViewModel @Inject constructor(private val repository: TaskRepositoryImpl): ViewModel() {

    private val selectedDate = MutableStateFlow(LocalDate.now())

    private val listTasks =
        selectedDate.flatMapLatest { date ->
            repository.getTasksByDate(date)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private var _dateUiState = MutableStateFlow(DateUiState())

    val dateUiState = combine(_dateUiState, listTasks, selectedDate) { dateUiState, listTasks, selectedDate ->
        dateUiState.copy(
            selectedDate = selectedDate,
            listTasks = listTasks,
            hasTasks = listTasks.any { it.dueDate == selectedDate }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DateUiState())

    fun insertTask(task: Task) {
        viewModelScope.launch {
            repository.insertTask(task)
        }
    }

    fun setSelectedDate(date: LocalDate) {
        selectedDate.value = date
    }
}