package com.task.management.data.repositories

import com.task.management.data.local.Task
import com.task.management.data.local.TaskDao
import com.task.management.domain.repositories.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(private val taskDao: TaskDao): TaskRepository {

    override suspend fun upsertTask(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.upsertTask(task)
        }
    }

}