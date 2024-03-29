package com.jetpackcompose.playground.task_room.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.jetpackcompose.playground.R
import com.jetpackcompose.playground.common.presentation.components.CustomTopAppBar
import com.jetpackcompose.playground.common.presentation.main.Screen
import com.jetpackcompose.playground.task_room.domain.data.Task
import com.jetpackcompose.playground.task_room.presentation.components.TaskListItem
import kotlinx.coroutines.CoroutineScope

@Composable
fun TaskScreen(
    navController: NavHostController,
    taskViewModel: TaskViewModel,
    scope: CoroutineScope,
    drawerState: DrawerState
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(stringResource(R.string.tasks), scope, drawerState)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {
                val nav = NavOptions.Builder().build()
                Screen.Task.NewTask.navigate(navController, nav)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add task")
                Text(text = "Add task")
            }
        }) { scaffoldPading ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPading)
        )
        {
            SearchRepoScreenContent(taskViewModel)
        }
    }
}

@Composable
private fun SearchRepoScreenContent(taskViewModel: TaskViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val tasks by taskViewModel.tasks.collectAsStateWithLifecycle()
        Tasks(tasks = tasks, onDelete = taskViewModel::deleteTask)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun Tasks(tasks: List<Task>, onDelete: (Task) -> Unit) {
//    tasks.onSuccess { repos ->
    val taskList = tasks.take(50)
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(taskList.count()) { itemId ->
            val task = taskList[itemId]
            AnimatedVisibility(
                visible = true,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Box(modifier = Modifier.animateEnterExit(enter = scaleIn(), exit = scaleOut())) {
                    TaskListItem(task, onItemClick = {

                    }, onDelete = {
                        onDelete(task)
                    })
                    HorizontalDivider()
                }
            }
        }
        if (tasks.count() == 0) {
            item {
                Text(
                    text = "No tasks found", textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}