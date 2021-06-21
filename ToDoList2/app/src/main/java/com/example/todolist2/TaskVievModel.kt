package com.example.todolist2


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allTasks: LiveData<List<Task>> = repository.allTasks.asLiveData()
    val sortDataByDate: LiveData<List<Task>> = repository.sortDataByDate.asLiveData()
    val sortDataByIcon: LiveData<List<Task>> = repository.sortDataByIcon.asLiveData()
    val sortDataByEndDate: LiveData<List<Task>> = repository.sortDataByEndDate.asLiveData()
    val sortDataByPriority: LiveData<List<Task>> = repository.sortDataByPriority.asLiveData()
    val sortDataByLocalization: LiveData<List<Task>> = repository.sortDataByLocalization.asLiveData()
   /* val tasksOfTheDay: LiveData<List<Task>> = repository.tasksOfTheDay.asLiveData()*/
    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(task: Task) = viewModelScope.launch {
        repository.insert(task)
    }

    fun deleteItem(task: Task) = viewModelScope.launch {
        repository.delete(task)
    }
}

class TaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}