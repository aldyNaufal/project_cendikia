package com.example.cendikia.data



import kotlinx.coroutines.flow.Flow

class OfflineItemsRepository(private val taskDao: TaskDao) : TaskRepository {

    override fun getAllTaskStream(): Flow<List<Task>> = taskDao.getAllTasks()

    override fun getTaskById(id: Int): Flow<Task?> = taskDao.getTaskById(id)

    override suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    override suspend fun updateTaskCompletionStatus(id: Int, isCompleted: Boolean) {
        taskDao.updateTaskCompletionStatus(id, isCompleted)
    }
}