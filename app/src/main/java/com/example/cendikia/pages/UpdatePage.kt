package com.example.cendikia.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cendikia.model.TaskViewModel
import com.example.cendikia.model.TaskViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePage(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    taskId: Int = -1,
    taskViewModel: TaskViewModel = viewModel(factory = TaskViewModelFactory(LocalContext.current))
) {
    val task = taskViewModel.getTask(taskId).collectAsState(initial = null).value

    if (task == null) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        return
    }

    var updatedTaskName by remember { mutableStateOf(task.name) }
    var showError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Update Task") },
                navigationIcon = {
                    IconButton(onClick = { navController?.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back to Home"
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                // Menampilkan detail task
                Text(
                    text = "Task Details:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(text = "ID: ${task.id}", fontSize = 14.sp)
                Text(text = "Name: ${task.name}", fontSize = 14.sp)
                Text(
                    text = "Timestamp: ${
                        java.text.SimpleDateFormat(
                            "dd MMM yyyy, HH:mm",
                            java.util.Locale.getDefault()
                        ).format(java.util.Date(task.timestamp))
                    }",
                    fontSize = 14.sp
                )
                Text(
                    text = "Status: ${if (task.isCompleted) "Completed" else "Pending"}",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Input nama task baru
                OutlinedTextField(
                    value = updatedTaskName,
                    onValueChange = { updatedTaskName = it },
                    label = { Text("Nama Task") },
                    placeholder = { Text("Masukkan nama task baru") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Menampilkan error jika nama kosong
                if (showError) {
                    Text(
                        text = "Nama task tidak boleh kosong!",
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // Tombol update task
                Button(
                    onClick = {
                        if (updatedTaskName.isBlank()) {
                            showError = true
                        } else {
                            val updatedTask = task.copy(name = updatedTaskName)
                            taskViewModel.updateTask(updatedTask)
                            navController?.popBackStack()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A))
                ) {
                    Text("Update Task", color = Color.White)
                }
            }
        }
    )
}





@Preview(showBackground = true)
@Composable
fun UpdatePageReview() {
    UpdatePage()
}