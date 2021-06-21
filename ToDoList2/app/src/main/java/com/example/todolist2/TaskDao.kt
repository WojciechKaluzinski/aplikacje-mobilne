package com.example.todolist2

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime

@Dao
interface TaskDao {
    /*tutaj będzie pewnie jeszcze kilka komend */


    @Query("SELECT * FROM task_table ORDER BY task_name ASC")
    fun getTasksSortedByName(): Flow<List<Task>>

    @Query("SELECT * FROM task_table ORDER BY task_start_date ASC")
    fun getTasksSortedByDate(): Flow<List<Task>>

    @Query("SELECT * FROM task_table ORDER BY task_icon ASC")
    fun getTasksSortedByIcon(): Flow<List<Task>>

    @Query("SELECT * FROM task_table ORDER BY task_end_date ASC")
    fun getTasksSortedByEndDate(): Flow<List<Task>>

    @Query("SELECT * FROM task_table ORDER BY task_priority DESC")
    fun getTasksSortedByPriority(): Flow<List<Task>>

    @Query("SELECT * FROM task_table ORDER BY task_localization DESC")
    fun getTasksSortedByLocalization(): Flow<List<Task>>

/*    @Query("SELECT * FROM task_table WHERE task_end_date BETWEEN :startDay AND :endDay")
    fun getTasksOfTheDay(startDay: Long, endDay: Long): Flow<List<Task>>*/



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)


    @Query("DELETE FROM task_table")
    suspend fun deleteAll()

}