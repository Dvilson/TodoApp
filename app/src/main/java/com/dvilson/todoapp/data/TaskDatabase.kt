package com.dvilson.todoapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dvilson.todoapp.di.AppCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class],version = 1)
abstract class TaskDatabase :RoomDatabase(){
    abstract fun taskDao():TaskDao


    class CallBack @Inject constructor(
        private  val taskDatabase: Provider<TaskDatabase>,
        @AppCoroutineScope private val  applicationScope:CoroutineScope
    ):RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = taskDatabase.get().taskDao()
            applicationScope.launch {
                dao.insert(Task(name = "Méditer"))
                dao.insert(Task(name = "Faire le Sport", completed =  true ))
                dao.insert(Task(name = "Lire 20 pages de ma lecture actuelle",important = true))
                dao.insert(Task(name = "Préparer mon petit dej", completed = true))

            }

        }
    }

}