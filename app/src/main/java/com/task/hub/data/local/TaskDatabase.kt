package com.task.hub.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.task.hub.data.convertors.ListConvertor
import com.task.hub.data.convertors.LocalDateConverter

@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(LocalDateConverter::class, ListConvertor::class)
abstract class TaskDatabase: RoomDatabase() {

    abstract val taskDao: TaskDao
}