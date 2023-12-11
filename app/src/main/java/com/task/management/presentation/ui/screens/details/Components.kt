package com.task.management.presentation.ui.screens.details

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.task.management.R
import com.task.management.data.local.Task
import com.task.management.presentation.ui.theme.Black
import com.task.management.presentation.ui.theme.Blue
import com.task.management.presentation.ui.theme.Grey
import com.task.management.presentation.ui.theme.White
import com.task.management.presentation.ui.theme.priegoFont
import compose.icons.FeatherIcons
import compose.icons.feathericons.Calendar
import compose.icons.feathericons.ChevronLeft
import compose.icons.feathericons.Edit3
import compose.icons.feathericons.Share2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import java.time.format.DateTimeFormatter

@Composable
fun DetailsContent(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(Black, RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
    ) {
        Column {
            TabRows()
        }
    }
}

@Composable
fun TopBarComponent(
    destinationsNavigator: DestinationsNavigator,
    task: Task
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(272.dp)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(21.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = FeatherIcons.ChevronLeft,
                contentDescription = null,
                tint = Grey,
                modifier = Modifier.size(26.dp).clickable {
                    destinationsNavigator.popBackStack()
                }
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Icon(
                    imageVector = FeatherIcons.Share2,
                    contentDescription = null,
                    tint = Grey,
                    modifier = Modifier.size(22.dp)
                )
                Icon(
                    imageVector = FeatherIcons.Edit3,
                    contentDescription = null,
                    tint = Grey,
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        Column(
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 2.dp)
        ) {
            Text(
                text = task.title,
                fontSize = 25.sp,
                color =  Black,
                fontFamily = priegoFont,
                fontWeight = FontWeight.Medium
            )

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 23.dp),
                horizontalArrangement = Arrangement.spacedBy(38.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile_photo),
                        contentDescription = "Profile Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = "Assigned to",
                            fontSize = 13.sp,
                            color =  Black,
                            fontFamily = priegoFont,
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            text = "Floyd Wilson",
                            fontSize = 14.sp,
                            color =  Black,
                            fontFamily = priegoFont,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(33.dp)
                            .height(33.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Creating a Canvas to draw a Circle
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val canvasWidth = size.width
                            val canvasHeight = size.height

                            drawCircle(
                                color = Grey,
                                center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                                radius = size.minDimension/2,
                                style = Stroke(width = 4f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(9f, 9f), 0f))
                            )
                        }
                        Icon(
                            imageVector = FeatherIcons.Calendar,
                            contentDescription = null,
                            tint = Black,
                            modifier = Modifier.size(17.dp)
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = "Due Date",
                            fontSize = 13.sp,
                            color =  Black,
                            fontFamily = priegoFont,
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            text = DateTimeFormatter.ofPattern("dd MMM").format(task.dueDate),
                            fontSize = 14.sp,
                            color =  Black,
                            fontFamily = priegoFont,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

data class TabItem(val title: String)

@Composable
fun TabRows() {
    val tabItems = listOf(
        TabItem(title = "Overview"),
        TabItem(title = "Activity")
    )

    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 18.dp)
        .padding(top = 16.dp)) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            contentColor = White,
            indicator = { tabPositions ->
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .height(2.dp)
                        .background(color = Blue)
                )
            },
            divider = {
                Box(
                    modifier = Modifier
                    .height(2.dp)
                    .alpha(0.25f)
                    .background(color = White)
                )
            }
        ) {
            tabItems.forEachIndexed { index, item ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = {
                        selectedTabIndex = index
                    },
                    text = {
                        Text(
                            text = item.title,
                            fontSize = 15.sp,
                            fontFamily = priegoFont,
                            color = White,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.alpha(if (index == selectedTabIndex) 1f else 0.4f)
                        )
                    },
                    interactionSource = object : MutableInteractionSource {
                        override val interactions: Flow<Interaction> = emptyFlow()

                        override suspend fun emit(interaction: Interaction) {}

                        override fun tryEmit(interaction: Interaction) = true
                    }
                )
            }
        }
    }
}