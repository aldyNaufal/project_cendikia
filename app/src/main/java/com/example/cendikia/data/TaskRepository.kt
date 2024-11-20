package com.example.cendikia.data


import kotlinx.coroutines.flow.Flow


interface TaskRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllTaskStream(): Flow<List<Task>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getTaskById(id: Int): Flow<Task?>

    /**
     * Insert item in the data source
     */
    suspend fun insertTask(task: Task)

    /**
     * Delete item from the data source
     */
    suspend fun deleteTask(task: Task)

    /**
     * Update item in the data source
     */
    suspend fun updateTask(task: Task)


    suspend fun updateTaskCompletionStatus(id: Int, isCompleted: Boolean)
}