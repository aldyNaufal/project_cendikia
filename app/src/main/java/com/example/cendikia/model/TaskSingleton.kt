package com.example.cendikia.model

import android.content.Context
import com.example.cendikia.data.OfflineItemsRepository
import com.example.cendikia.data.TaskDatabase
import com.example.cendikia.data.TaskRepository

object TaskSingleton {
    @Volatile
    private var instance: TaskRepository? = null

    fun getTaskRepository(context: Context): TaskRepository {

        return instance ?: synchronized(this) {
            val database = TaskDatabase.getDatabase(context.applicationContext)
            OfflineItemsRepository(database.taskDao()).also { instance = it }
        }
    }
}