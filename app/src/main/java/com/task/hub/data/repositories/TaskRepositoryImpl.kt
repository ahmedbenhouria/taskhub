package com.task.hub.data.repositories

import com.task.hub.data.local.Task
import com.task.hub.data.local.TaskDao
import com.task.hub.domain.repositories.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(private val taskDao: TaskDao): TaskRepository {

    override suspend fun insertTask(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.insertTask(task)
        }
    }

    override suspend fun getAllTasks(): List<Task> {
        return taskDao.getAllTasks()
    }

}