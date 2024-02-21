package com.task.hub.presentation.ui.screen.add

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.task.hub.presentation.ui.theme.Black
import com.task.hub.presentation.ui.theme.White
import com.task.hub.presentation.ui.theme.priegoFont
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun AddScreen(
    destinationsNavigator: DestinationsNavigator,
    viewModel: AddTaskViewModel = viewModel()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )

        var isSheetOpen by remember {
            mutableStateOf(true)
        }

        val scope = rememberCoroutineScope()

        if (isSheetOpen) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    isSheetOpen = false
                    destinationsNavigator.popBackStack()
                },
                containerColor = Black,
                modifier = Modifier.padding(top = 7.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 17.dp)
                    ) {
                        Text(
                            text = "Cancel",
                            fontSize = 16.sp,
                            color = Color(0xFF40629B),
                            fontFamily = priegoFont,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.clickable {
                                scope.launch {
                                    sheetState.hide()
                                    viewModel.resetTaskState()
                                }
                                destinationsNavigator.popBackStack()
                            }
                        )
                        Text(
                            text = "New Task",
                            fontSize = 16.sp,
                            color =  White,
                            fontFamily = priegoFont,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(end = 10.dp)
                        )
                        Text(
                            text = "Done",
                            fontSize = 16.sp,
                            color = Color(0xFF40629B),
                            fontFamily = priegoFont,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.clickable {
                                viewModel.onEvent(TaskFormEvent.Submit)
                            }
                        )
                    }

                    CreateTaskContent(onHideSheet = {
                        if (it) {
                            scope.launch {
                                sheetState.hide()
                                viewModel.resetTaskState()
                            }
                            destinationsNavigator.popBackStack()
                        }
                    })
                }
            }
        }
    }
}
