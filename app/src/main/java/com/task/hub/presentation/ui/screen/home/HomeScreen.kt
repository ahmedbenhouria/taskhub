package com.task.hub.presentation.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.task.hub.presentation.ui.theme.Black

@Destination(start = true)
@Composable
fun HomeScreen(destinationsNavigator: DestinationsNavigator) {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            Color.Transparent,
            darkIcons = false
        )
    }

    Scaffold(
        containerColor = Black,
        topBar = {
            TopBarComponent()
        }
    ) { paddingValues ->
        Content(paddingValues, destinationsNavigator)
    }
}

@Composable
fun Content(
    paddingValues: PaddingValues,
    destinationsNavigator: DestinationsNavigator
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .padding(top = 5.dp)
    ) {
        InlineTitleIconComponent()
        WeekCalenderSection(destinationsNavigator)
    }
}

