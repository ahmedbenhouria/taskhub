package com.task.management.ui.screens.homeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.task.management.ui.theme.Black

@Destination(start = true)
@Composable
fun Home(destinationsNavigator: DestinationsNavigator) {
    Scaffold(
        containerColor = Black,
        topBar = {
            TopBarComponent()
        }
    ) { paddingValues ->
        Content(paddingValues = paddingValues)
    }
}

@Composable
fun Content(paddingValues: PaddingValues) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .padding(top = 5.dp)
    ) {
        InlineTitleIconComponent()
        WeekCalenderSection()
    }
}

