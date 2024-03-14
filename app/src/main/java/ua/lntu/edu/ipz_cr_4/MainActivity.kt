package ua.lntu.edu.ipz_cr_4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import java.util.*
import ua.lntu.edu.ipz_cr_4.ui.theme.IPZ_CR_4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IPZ_CR_4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TaskManager()
                }
            }
        }
    }
}

@Composable
fun TaskManager() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "taskList") {
        composable("taskList") {
            TaskListScreen(navController)
        }
        composable("taskDetail/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            taskId?.let {
                TaskDetailScreen(taskId = it, navController = navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(navController: NavHostController) {
    val tasks = remember { generateTaskList() }
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Список завдань") }) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            tasks.forEach { task ->
                TaskItem(task = task, onClick = {
                    navController.navigate("taskDetail/${task.id}")
                })
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onClick: () -> Unit) {
    Card(
        colors = if (task.status == TaskStatus.ACTIVE) Color.Green else Color.Red,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = task.name, style = MaterialTheme.typography.bodyMedium, color = Color.White)
        }
    }
}

data class Task(val id: String, val name: String, val description: String, val date: Date, val status: TaskStatus)

enum class TaskStatus {
    ACTIVE, DONE
}

fun generateTaskList(): List<Task> {
    return listOf(
        Task("1", "Завдання 1", "Опис завдання 1", Date(), TaskStatus.ACTIVE),
        Task("2", "Завдання 2", "Опис завдання 2", Date(), TaskStatus.DONE),
        Task("3", "Завдання 3", "Опис завдання 3", Date(), TaskStatus.ACTIVE)
    )
}