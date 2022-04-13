package com.dvilson.todoapp.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dvilson.todoapp.data.TaskDao
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor( private val dao: TaskDao): ViewModel() {

    val serchQuery = MutableStateFlow("")

   private val tasksflow= serchQuery.flatMapLatest {
       dao.getTasks(it)

    }

    val tasks = tasksflow.asLiveData()


}