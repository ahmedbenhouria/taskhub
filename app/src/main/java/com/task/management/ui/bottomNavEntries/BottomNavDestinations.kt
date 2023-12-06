package com.task.management.ui.bottomNavEntries

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.task.management.R
import com.task.management.ui.screens.destinations.AddDestination
import com.task.management.ui.screens.destinations.HomeDestination
import com.task.management.ui.screens.destinations.MessageDestination
import com.task.management.ui.screens.destinations.ProfileDestination
import com.task.management.ui.screens.destinations.TasksDestination
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
    Home(HomeDestination, FeatherIcons.Home, R.string.home),
    Tasks(TasksDestination, FeatherIcons.Folder, R.string.tasks),
    Add(AddDestination, FeatherIcons.Plus, R.string.add),
    Message(MessageDestination, FeatherIcons.MessageCircle, R.string.message),
    Profile(ProfileDestination, FeatherIcons.User, R.string.profile)
}