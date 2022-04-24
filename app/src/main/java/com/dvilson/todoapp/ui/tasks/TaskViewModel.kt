package com.dvilson.todoapp.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dvilson.todoapp.data.PreferenceManager
import com.dvilson.todoapp.data.SortOrder
import com.dvilson.todoapp.data.TaskDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class TaskViewModel @Inject constructor
    (
    private val dao: TaskDao,
    private val preferenceManager: PreferenceManager

) : ViewModel() {

    val serchQuery = MutableStateFlow("")
    val preferencesFlow = preferenceManager.preferencesFlow


    private val tasksflow = combine(serchQuery, preferencesFlow) { query, filterPreference ->
        Pair(query, filterPreference)

    }.flatMapLatest { (query, filterPreference) ->
        dao.getTasks(query, filterPreference.sortOrder, filterPreference.hideCompleted)

    }

    val tasks = tasksflow.asLiveData()


    fun onSortOrderSelected(sortOrder: SortOrder) =
        viewModelScope.launch { preferenceManager.updateSortedOrder(sortOrder) }

    fun onHideCompletedClick(hideCompleted:Boolean) =
        viewModelScope.launch { preferenceManager.updateHideCompled(hideCompleted) }


}

