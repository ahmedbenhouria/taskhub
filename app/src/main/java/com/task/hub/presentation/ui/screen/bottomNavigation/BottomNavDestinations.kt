package com.task.hub.presentation.ui.screen.bottomNavigation

import androidx.annotation.StringRes
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.task.hub.R
import com.task.hub.presentation.ui.screen.destinations.AddScreenDestination
import com.task.hub.presentation.ui.screen.destinations.HomeScreenDestination
import com.task.hub.presentation.ui.screen.destinations.MessageScreenDestination
import com.task.hub.presentation.ui.screen.destinations.ProfileScreenDestination
import com.task.hub.presentation.ui.screen.destinations.TasksScreenDestination

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val unselectedIcon: Int,
    val selectedIcon: Int,
    @StringRes val label: Int
) {
    Home(HomeScreenDestination, R.drawable.house_regular, R.drawable.house_solid, R.string.home),
    Tasks(TasksScreenDestination, R.drawable.folder_regular, R.drawable.folder_solid, R.string.tasks),
    Add(AddScreenDestination, R.drawable.plus_icon, R.drawable.plus_icon, R.string.add),
    Message(MessageScreenDestination, R.drawable.comment_regular, R.drawable.comment_solid, R.string.message),
    Profile(ProfileScreenDestination, R.drawable.user_regular, R.drawable.user_solid, R.string.profile)
}