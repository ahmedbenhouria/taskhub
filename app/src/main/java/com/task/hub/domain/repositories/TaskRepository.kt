package com.task.hub.domain.repositories

import com.task.hub.data.local.Task

interface TaskRepository {
    suspend fun insertTask(task: Task)

    suspend fun getAllTasks(): List<Task>

}