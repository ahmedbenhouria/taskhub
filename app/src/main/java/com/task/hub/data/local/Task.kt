package com.task.hub.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.task.hub.presentation.ui.screen.add.Member
import kotlinx.parcelize.RawValue
import java.time.LocalDate

@Entity (tableName = "Task")
data class Task(
    @PrimaryKey (autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val dueDate: LocalDate = LocalDate.now(),
    val estimateTime: Int = 0,
    val priority: String = "",
    val members: @RawValue List<Member> = emptyList()
)
