package com.task.hub.data.di

import android.content.Context
import androidx.room.Room
import com.task.hub.data.local.TaskDao
import com.task.hub.data.local.TaskDatabase
import com.task.hub.data.repositories.TaskRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun getRepository(dao: TaskDao): TaskRepositoryImpl {
        return TaskRepositoryImpl(dao)
    }

    @Singleton
    @Provides
    fun getDao(database: TaskDatabase): TaskDao {
        return database.taskDao
    }

    @Provides
    @Singleton
    fun provideTaskDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            "task_database"
        ).build()
}