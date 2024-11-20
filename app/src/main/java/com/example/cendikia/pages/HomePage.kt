package com.example.cendikia.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cendikia.model.AuthState
import com.example.cendikia.model.AuthViewModel
import com.example.cendikia.model.TaskItem
import com.example.cendikia.model.TaskViewModel
import com.example.cendikia.model.TaskViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    authViewModel: AuthViewModel? = null
) {
    val context = LocalContext.current
    val taskViewModel: TaskViewModel = viewModel(factory = TaskViewModelFactory(context))
    val allTasks by taskViewModel.allTasks.collectAsState(initial = emptyList())

    val authState = authViewModel?.authState?.observeAsState(initial = AuthState.Loading)?.value

    if (authState is AuthState.Unauthenticated) {
        LaunchedEffect(Unit) {
            navController?.navigate("login") {
                popUpTo("home") { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Home Page") })
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                if (authState is AuthState.Authenticated) {
                    val userEmail = (authState as AuthState.Authenticated).email
                    Column(modifier = Modifier.padding(bottom = 16.dp)) {
                        Text(
                            text = "Selamat Datang",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                        Text(
                            text = userEmail ?: "User",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(allTasks.filter { !it.isCompleted }) { task ->
                        TaskItem(
                            task = task,
                            onDelete = { taskViewModel.deleteTask(task) },
                            onEdit = { navController?.navigate("update/${task.id}") }, // Simplified navigation
                            onStatusChange = { isCompleted ->
                                taskViewModel.updateTaskStatus(task.id, isCompleted)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController?.navigate("add") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A))
                ) {
                    Text("Tambah Task", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController?.navigate("search") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A))
                ) {
                    Text("Cari Task", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                authViewModel?.let { viewModel ->
                    Button(
                        onClick = {
                            viewModel.signout()
                            navController?.navigate("signin") {
                                popUpTo("home") { inclusive = true }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Logout", color = Color.White)
                    }
                }
            }
        }
    )
}




@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    HomePage()
}



