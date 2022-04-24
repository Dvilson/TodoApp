package com.dvilson.todoapp.data

import androidx.room.*
import com.dvilson.todoapp.ui.tasks.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task:Task)
    @Update
    suspend fun  update(task: Task)
    @Delete
    suspend fun  delete(task: Task)
    fun getTasks(query: String,sortOrder: SortOrder,hideCompleted:Boolean):Flow<List<Task>> =
        when(sortOrder){
            SortOrder.BY_NAME -> getTasksByName(searchQuery = query,hideCompleted)
            SortOrder.BY_DATE-> getTasksByDate(searchQuery = query,hideCompleted)
        }



    @Query("SELECT * FROM task_table WHERE(completed!=:hideCompleted OR completed = 0) AND name LIKE '%'|| :searchQuery || '%' ORDER BY important DESC,name")
    fun getTasksByName(searchQuery:String,hideCompleted: Boolean):Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE(completed !=:hideCompleted OR completed = 0) AND name LIKE '%'|| :searchQuery || '%' ORDER BY important DESC,created")
    fun getTasksByDate(searchQuery:String,hideCompleted: Boolean):Flow<List<Task>>


}