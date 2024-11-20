package com.example.cendikia.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cendikia.data.Task
import com.example.cendikia.data.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {

    private lateinit var taskRepository: TaskRepository

    private var currentTaskId: Int? = null // Untuk menyimpan ID task sementara

    // Fungsi untuk menginisialisasi TaskRepository
    fun initialize(taskRepository: TaskRepository) {
        this.taskRepository = taskRepository
    }

    // Mendapatkan semua task
    val allTasks: Flow<List<Task>>
        get() = taskRepository.getAllTaskStream()

    // Menyimpan ID task untuk navigasi
    fun saveTaskId(taskId: Int) {
        currentTaskId = taskId
    }

    // Mendapatkan task berdasarkan ID
    fun getTaskById(): Flow<Task?> {
        val taskId = currentTaskId
        return if (taskId != null) {
            taskRepository.getTaskById(taskId)
        } else {
            flowOf(null)
        }
    }

    // Menambahkan task baru
    fun addTask(name: String) {
        val newTask = Task(name = name, timestamp = System.currentTimeMillis())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                taskRepository.insertTask(newTask)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Memperbarui task
    fun updateTask(task: Task) {
        val updatedTask = task.copy(timestamp = System.currentTimeMillis())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                taskRepository.updateTask(updatedTask)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Menghapus task
    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                taskRepository.deleteTask(task)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Memperbarui status task
    fun updateTaskStatus(taskId: Int, isCompleted: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val task = taskRepository.getTaskById(taskId).firstOrNull()
            task?.let {
                val updatedTask = it.copy(isCompleted = isCompleted)
                taskRepository.updateTask(updatedTask)
            }
        }
    }

    fun getTask(id: Int): Flow<Task?> {
        return if (id != -1) {
            taskRepository.getTaskById(id)
        } else {
            flowOf(null)
        }
    }
}
