package com.task.hub.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.task.hub.presentation.ui.screen.add.Member
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.time.LocalDate

@Parcelize
@Entity (tableName = "Task")
data class Task(
    @PrimaryKey (autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val dueDate: LocalDate,
    val estimateTime: Int,
    val priority: String,
    val members: @RawValue List<Member>
): Parcelable
