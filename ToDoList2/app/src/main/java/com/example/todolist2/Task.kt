package com.example.todolist2

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

import java.sql.Time
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "task_table")
class Task(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "task_name") val task_name: String?,
    @ColumnInfo(name = "task_start_date") val task_start_date: LocalDateTime?,
    @ColumnInfo(name = "task_end_date") val task_end_date: LocalDateTime?,
    @ColumnInfo(name = "task_text") val task_text: String?,
    @ColumnInfo(name = "task_icon") val task_icon: Images?,
    @ColumnInfo(name = "task_priority") val task_priority: String?,
    @ColumnInfo(name = "task_localization") val task_localization: String?
    /*@ColumnInfo(name = "task_icon") val task_icon: String?*/
    /*@ColumnInfo(name = "task_text") val task_text: String?,
    @ColumnInfo(name = "task_date") val task_date: Date?,
    @ColumnInfo(name = "task_time") val task_time: Time?,
    @ColumnInfo(name = "task_icon") val task_icon: Int?*/

)

enum class Images{
    STAR, CHECK, CODE, CLEAN, MONEY, SHOOL
}