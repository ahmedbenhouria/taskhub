package com.task.management.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.task.management.presentation.ui.screens.add.Member
import java.time.LocalDate

@Entity (tableName = "Task")
data class Task(
    @PrimaryKey (autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val dueDate: LocalDate,
    val estimateTime: Int,
    val priority: String,
    val members: List<Member>
)
