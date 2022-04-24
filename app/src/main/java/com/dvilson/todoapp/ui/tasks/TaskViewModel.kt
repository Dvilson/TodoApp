package com.dvilson.todoapp.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dvilson.todoapp.data.TaskDao
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val dao: TaskDao) : ViewModel() {

    val serchQuery = MutableStateFlow("")
    val sortOrder = MutableStateFlow(SortOrder.BY_DATE)
    val hideCompleted = MutableStateFlow(false)


    private val tasksflow = combine(serchQuery,
        sortOrder,
        hideCompleted) {query, sortOrder, hideCompleted->
        Triple(query,sortOrder,hideCompleted)

    }.flatMapLatest {(query,sortOrder,hideCompleted) ->
        dao.getTasks(query,sortOrder,hideCompleted)

    }

    val tasks = tasksflow.asLiveData()


}

enum class SortOrder {
    BY_NAME,
    BY_DATE
}