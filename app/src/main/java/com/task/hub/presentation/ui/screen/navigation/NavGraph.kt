package com.task.hub.presentation.ui.screen.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.task.hub.presentation.ui.screen.MessageScreen
import com.task.hub.presentation.ui.screen.ProfileScreen
import com.task.hub.presentation.ui.screen.TasksScreen
import com.task.hub.presentation.ui.screen.add.AddTaskScreen
import com.task.hub.presentation.ui.screen.details.DetailsScreen
import com.task.hub.presentation.ui.screen.home.HomeScreen

@Composable
fun MainNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.BottomNavGraph,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        bottomNavGraph(navController)

        composable<Destinations.Details> {
            DetailsScreen(navController)
        }
    }
}

fun NavGraphBuilder.bottomNavGraph(
    navController: NavHostController
) {
    navigation<Destinations.BottomNavGraph>(
        startDestination = Destinations.Home,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        composable<Destinations.Home> {
            HomeScreen(navController)
        }
        composable<Destinations.Tasks> {
            TasksScreen()
        }
        composable<Destinations.AddTask> {
            AddTaskScreen(navController)
        }
        composable<Destinations.Message> {
            MessageScreen()
        }
        composable<Destinations.Profile> {
            ProfileScreen()
        }
    }
}