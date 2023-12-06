package com.task.management

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.rememberNavHostEngine
import com.task.management.ui.bottomNavEntries.BottomBarDestination
import com.task.management.ui.screens.NavGraphs
import com.task.management.ui.theme.Black
import com.task.management.ui.theme.White


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ParentBottomNav() {
    val navController = rememberNavController()
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            Color.Transparent,
            darkIcons = false
        )
    }

    Scaffold(
        containerColor = Black,
        bottomBar = {
            BottomBar(navController)
        }

    ) {
        val navHostEngine = rememberNavHostEngine(
            rootDefaultAnimations = RootNavGraphDefaultAnimations(
                enterTransition = { fadeIn(animationSpec = tween(400)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
        ))

        DestinationsNavHost(
            navController = navController,
            navGraph = NavGraphs.root,
            engine = navHostEngine
        )
    }
}

@Composable
fun BottomBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = Black,
        contentColor = Black,
        modifier = Modifier.height(100.dp)
    ) {
        BottomBarDestination.values().forEach { destination ->
            AddItem(
                destination = destination,
                currentDestination = currentDestination,
            ) {
                navController.navigate(destination.direction) {
                    launchSingleTop = true
                    val firstBottomBarDestination = navController.graph.findStartDestination()

                    popUpTo(firstBottomBarDestination.id) {
                        inclusive = true
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }

}

@Composable
fun RowScope.AddItem(
    destination: BottomBarDestination,
    currentDestination: NavDestination?,
    onClickItem: () -> Unit
) {
    val isNavItemSelected = currentDestination?.hierarchy?.any { it.route == destination.direction.route } == true

    NavigationBarItem(
        selected = isNavItemSelected,
        onClick = onClickItem,
        icon = {
            when (destination.label) {
                R.string.add -> {
                    NavIconComponent(
                        destination = destination,
                        isNavItemSelected = isNavItemSelected,
                        modifier = Modifier
                            .size(58.dp)
                            .background(
                                color = White,
                                shape = CircleShape,
                            )
                            .padding(13.dp)
                    )
                }
                else -> {
                    NavIconComponent(
                        destination = destination,
                        isNavItemSelected = isNavItemSelected,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.White,
            indicatorColor = Black
        )
    )
}

@Composable
fun NavIconComponent(
    destination: BottomBarDestination,
    isNavItemSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = destination.icon,
        contentDescription = stringResource(destination.label),
        tint = when (destination.label) {
            R.string.add -> {
                if (isNavItemSelected) {
                    Color.Gray
                } else
                    Black
            }

            else -> {
                if (isNavItemSelected) {
                    Color.Gray
                } else
                    White
            }
        },
        modifier = modifier
    )
}