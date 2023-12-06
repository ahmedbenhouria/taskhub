package com.task.management.presentation.ui.bottomNav

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.task.management.R
import com.task.management.presentation.ui.screens.destinations.AddScreenDestination
import com.task.management.presentation.ui.screens.destinations.HomeScreenDestination
import com.task.management.presentation.ui.screens.destinations.MessageScreenDestination
import com.task.management.presentation.ui.screens.destinations.ProfileScreenDestination
import com.task.management.presentation.ui.screens.destinations.TasksScreenDestination
import compose.icons.FeatherIcons
import compose.icons.feathericons.Folder
import compose.icons.feathericons.Home
import compose.icons.feathericons.MessageCircle
import compose.icons.feathericons.Plus
import compose.icons.feathericons.User

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    Home(HomeScreenDestination, FeatherIcons.Home, R.string.home),
    Tasks(TasksScreenDestination, FeatherIcons.Folder, R.string.tasks),
    Add(AddScreenDestination, FeatherIcons.Plus, R.string.add),
    Message(MessageScreenDestination, FeatherIcons.MessageCircle, R.string.message),
    Profile(ProfileScreenDestination, FeatherIcons.User, R.string.profile)
}