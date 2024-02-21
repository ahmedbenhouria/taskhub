package com.task.hub.domain.repositories

import com.task.hub.data.local.Task
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TaskRepository {
    suspend fun insertTask(task: Task)

    suspend fun getTasksByDate(selectedDate: LocalDate): Flow<List<Task>>

}