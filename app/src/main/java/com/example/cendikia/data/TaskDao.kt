package com.example.cendikia.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM task ORDER BY timestamp DESC")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE id = :id")
    fun getTaskById(id: Int): Flow<Task?>

    @Query("SELECT * FROM task WHERE isCompleted = 0 ORDER BY timestamp DESC")
    fun getIncompleteTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE isCompleted = 1 ORDER BY timestamp DESC")
    fun getCompletedTasks(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("UPDATE task SET isCompleted = :isCompleted WHERE id = :id")
    suspend fun updateTaskCompletionStatus(id: Int, isCompleted: Boolean)
}
