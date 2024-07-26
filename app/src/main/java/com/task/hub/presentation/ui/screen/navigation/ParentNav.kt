package com.task.hub.presentation.ui.screen.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.task.hub.R
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
        MainNavGraph(navController)
    }
}

@Composable
fun BottomBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route
        ?: BottomNavScreens.Home.route::class.qualifiedName.orEmpty()

    val currentRouteTrimmed by remember(currentRoute) {
        derivedStateOf { currentRoute.substringAfterLast(".") }
    }

    var bottomBarState by rememberSaveable { (mutableStateOf(true)) }

    when (currentRouteTrimmed) {
        BottomNavScreens.Home.route.toString() -> {
            bottomBarState = true
        }
        BottomNavScreens.Message.route.toString() -> {
            bottomBarState = true
        }
        BottomNavScreens.Profile.route.toString() -> {
            bottomBarState = true
        }
        BottomNavScreens.Tasks.route.toString() -> {
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
            BottomNavScreens.entries.forEach { screen ->
                val isNavItemSelected = currentRouteTrimmed == screen.route.toString()

                AddItem(
                    screen = screen,
                    isSelected = isNavItemSelected,
                ) {
                    if (!this@AnimatedVisibility.transition.isRunning) {
                        navController.navigate(screen.route) {
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
}

@Composable
fun RowScope.AddItem(
    screen: BottomNavScreens,
    isSelected: Boolean,
    onClickItem: () -> Unit
) {

    NavigationBarItem(
        selected = isSelected,
        onClick = onClickItem,
        icon = {
            when (screen.label) {
                R.string.add -> {
                    NavIconComponent(
                        screen = screen,
                        isNavItemSelected = isSelected,
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
                        screen = screen,
                        isNavItemSelected = isSelected,
                        modifier = Modifier
                            .size(
                                when (screen.label) {
                                    R.string.home -> 29.dp
                                    R.string.profile -> 22.dp
                                    else -> 23.dp
                                }
                            )
                            .alpha(0.9f)
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
    screen: BottomNavScreens,
    isNavItemSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painterResource(
            id = if (isNavItemSelected)
                screen.selectedIcon
            else
                screen.unselectedIcon
        ),
        contentDescription = stringResource(screen.label),
        tint = when (screen.label) {
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