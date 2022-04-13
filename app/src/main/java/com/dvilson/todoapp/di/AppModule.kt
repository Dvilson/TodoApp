package com.dvilson.todoapp.di


import android.app.Application
import androidx.room.Room
import com.dvilson.todoapp.data.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun providesTaskDatabase(app: Application, callback: TaskDatabase.CallBack) =
        Room.databaseBuilder(
            app, TaskDatabase::class.java, "task_database"
        ).fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()

    @Singleton
    @Provides
    fun providesTaskDao(taskDatabase: TaskDatabase) = taskDatabase.taskDao()


    @AppCoroutineScope
    @Singleton
    @Provides
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class AppCoroutineScope