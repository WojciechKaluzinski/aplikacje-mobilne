package com.example.todolist2


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class TaskRepository (private val taskDao: TaskDao)   {



    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allTasks: Flow<List<Task>> = taskDao.getTasksSortedByName()
    val sortDataByDate: Flow<List<Task>> = taskDao.getTasksSortedByDate()
    val sortDataByIcon: Flow<List<Task>> = taskDao.getTasksSortedByIcon()
    val sortDataByEndDate: Flow<List<Task>> = taskDao.getTasksSortedByEndDate()
    val sortDataByPriority: Flow<List<Task>> = taskDao.getTasksSortedByPriority()
    val sortDataByLocalization: Flow<List<Task>> = taskDao.getTasksSortedByLocalization()
/*    val tasksOfTheDay: Flow<List<Task>> = taskDao.getTasksOfTheDay(startDay, endDay)*/
    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(task: Task) {
        taskDao.delete(task)
    }

}