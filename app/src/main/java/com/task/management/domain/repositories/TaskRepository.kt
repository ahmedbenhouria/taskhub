package com.task.management.domain.repositories

import androidx.lifecycle.LiveData
import com.task.management.data.local.Task

interface TaskRepository {
    suspend fun insertTask(task: Task)

    suspend fun getAllTasks(): List<Task>

}