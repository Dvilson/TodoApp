package com.dvilson.todoapp.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


private val Context.dataStore by preferencesDataStore(name = "users_preferences")

@Singleton
class PreferenceManager @Inject constructor(@ApplicationContext context: Context) {

    val dataStore = context.dataStore

    val preferencesFlow = dataStore.data
        .map { preferences ->
            val sortOrder =
                SortOrder.valueOf(preferences[PreferencesKey.SORT_ORDER] ?: SortOrder.BY_DATE.name)
            val hideCompleted = preferences[PreferencesKey.HIDE_COMPLETED] ?: false

            FilterPreference(sortOrder = sortOrder, hideCompleted = hideCompleted)


        }

    private object PreferencesKey {
        val SORT_ORDER = stringPreferencesKey(name = "sort_order")
        val HIDE_COMPLETED = booleanPreferencesKey(name = "hide_completed")


    }

    suspend fun updateSortedOrder(sortOrder: SortOrder) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.SORT_ORDER] = sortOrder.name

        }

    }

    suspend fun updateHideCompled(hideCompleted: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.HIDE_COMPLETED] = hideCompleted

        }
    }

}


data class FilterPreference(val sortOrder: SortOrder, val hideCompleted: Boolean)

enum class SortOrder {
    BY_NAME,
    BY_DATE
}
