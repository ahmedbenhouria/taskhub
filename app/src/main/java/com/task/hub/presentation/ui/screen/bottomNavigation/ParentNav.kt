package com.task.hub.presentation.ui.screen.bottomNavigation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
import com.task.hub.R
import com.task.hub.presentation.ui.screen.NavGraphs
import com.task.hub.presentation.ui.theme.Black
import com.task.hub.presentation.ui.theme.White


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ParentNav() {
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

    var bottomBarState by rememberSaveable { (mutableStateOf(true)) }

    when (navBackStackEntry?.destination?.route) {
        "home_screen" -> {
            bottomBarState = true
        }
        "message_screen" -> {
            bottomBarState = true
        }
        "profile_screen" -> {
            bottomBarState = true
        }
        "tasks_screen" -> {
            bottomBarState = true
        }
        else -> {
            bottomBarState = false
        }
    }

    AnimatedVisibility(
        visible = bottomBarState,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        NavigationBar(
            containerColor = Black,
            contentColor = Black,
            modifier = Modifier.height(100.dp)
        ) {
            BottomBarDestination.entries.forEach { destination ->
                AddItem(
                    destination = destination,
                    currentDestination = currentDestination,
                ) {
                    navController.navigate(destination.direction) {
                        launchSingleTop = true
                        val firstBottomBarDestination = navController.graph.findStartDestination()

                        popUpTo(firstBottomBarDestination.id) {
                            saveState = true
                        }
                        restoreState = true
                    }
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
                            .padding(11.dp)
                            .padding(end = 2.dp)
                    )
                }
                else -> {
                    NavIconComponent(
                        destination = destination,
                        isNavItemSelected = isNavItemSelected,
                        modifier = Modifier.size(
                            when (destination.label) {
                                R.string.home -> 29.dp
                                R.string.profile -> 22.dp
                                else -> 23.dp
                            }
                        ).alpha(0.9f)
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
        painter = painterResource(
            id = if (isNavItemSelected)
                destination.selectedIcon
            else
                destination.unselectedIcon
        ),
        contentDescription = stringResource(destination.label),
        tint = when (destination.label) {
            R.string.add -> {
                if (isNavItemSelected) {
                    Color.Gray
                } else
                    Black
            }
            else -> White
        },
        modifier = modifier
    )
}