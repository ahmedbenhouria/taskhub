package com.task.management.domain.repositories

import com.task.management.data.local.Task

interface TaskRepository {
    suspend fun upsertTask(task: Task)
}