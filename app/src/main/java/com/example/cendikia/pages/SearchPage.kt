package com.example.cendikia.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cendikia.data.Task
import com.example.cendikia.model.TaskViewModel
import com.example.cendikia.model.TaskViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPage(
    modifier: Modifier = Modifier,
    navController: NavController? = null
) {
    val context = LocalContext.current
    val taskViewModel: TaskViewModel = viewModel(factory = TaskViewModelFactory(context))

    var inputId by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var searchId by remember { mutableStateOf<Int?>(null) } // State untuk ID yang akan dicari

    // Mengumpulkan hasil pencarian berdasarkan searchId
    val task by taskViewModel.getTask(searchId ?: -1).collectAsState(initial = null)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search Task by ID") },
                navigationIcon = {
                    IconButton(onClick = { navController?.navigate("home") }) {
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
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                // Input field for ID
                OutlinedTextField(
                    value = inputId,
                    onValueChange = { inputId = it },
                    label = { Text("Task ID") },
                    placeholder = { Text("Enter Task ID") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Search button
                Button(
                    onClick = {
                        val id = inputId.toIntOrNull()
                        if (id == null) {
                            showError = true
                        } else {
                            showError = false
                            searchId = id // Tetapkan ID untuk pencarian
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Search")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Error message if input is invalid
                if (showError) {
                    Text(
                        text = "Please enter a valid ID!",
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Display task details if found
                if (task != null) {
                    Text(
                        text = "Task Details:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(text = "ID: ${task!!.id}", fontSize = 14.sp)
                    Text(text = "Name: ${task!!.name}", fontSize = 14.sp)
                    Text(
                        text = "Timestamp: ${
                            java.text.SimpleDateFormat(
                                "dd MMM yyyy, HH:mm",
                                java.util.Locale.getDefault()
                            ).format(java.util.Date(task!!.timestamp))
                        }",
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Status: ${if (task!!.isCompleted) "Completed" else "Pending"}",
                        fontSize = 14.sp
                    )
                } else if (!showError && searchId != null) {
                    Text(
                        text = "No task found with the given ID.",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    )
}



@Preview(showBackground = true)
@Composable
fun SearchPagePreview() {
    SearchPage()
}
