package com.task.hub.presentation.ui.screen.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.task.hub.R
import com.task.hub.data.local.Task
import com.task.hub.presentation.ui.screen.destinations.DetailsScreenDestination
import com.task.hub.presentation.ui.theme.Black
import com.task.hub.presentation.ui.theme.Blue
import com.task.hub.presentation.ui.theme.Grey
import com.task.hub.presentation.ui.theme.GreyLight
import com.task.hub.presentation.ui.theme.White
import com.task.hub.presentation.ui.theme.priegoFont
import compose.icons.FeatherIcons
import compose.icons.feathericons.Menu
import compose.icons.feathericons.MessageCircle
import compose.icons.feathericons.Paperclip
import compose.icons.feathericons.Share2
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun TopBarComponent() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 27.dp)
            .fillMaxWidth()
            .height(80.dp)

    ) {
        Icon(
            imageVector = FeatherIcons.Menu,
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .alpha(0.8F),
            tint = White
        )
        Image(
            painter = painterResource(id = R.drawable.profile_photo),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(41.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun InlineTitleIconComponent() {
    val myId = "inlineContent"
    val text = buildAnnotatedString {
        append("Manage\nyour tasks")
        // Append a placeholder string "[icon]" and attach an annotation "inlineContent" on it.
        appendInlineContent(myId, "[icon]")
    }

    val inlineContent = mapOf(
        Pair(
            myId,
            InlineTextContent(
                Placeholder(
                    width = 60.sp,
                    height = 60.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                )
            ) {
                Image(painter = painterResource(
                    id = R.drawable.pencil),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 8.dp, top = 7.dp)
                        .size(50.dp)
                )
            }
        )
    )

    Text(
        text = text,
        color = White,
        fontFamily = priegoFont,
        fontWeight = FontWeight.Normal,
        fontSize = 50.sp,
        lineHeight = 48.sp,
        modifier = Modifier.padding(start = 27.dp, end = 5.dp),
        inlineContent = inlineContent
    )
}

@Composable
fun WeekCalenderSection(
    destinationsNavigator: DestinationsNavigator,
    viewModel: TaskViewModel = hiltViewModel(),
) {
    val dateState by viewModel.dateUiState.collectAsStateWithLifecycle()

    val listTasks = dateState.listTasks
    val selectedDate = dateState.selectedDate

    val startDate = remember { selectedDate.minusDays(30) }
    val endDate = remember { selectedDate.plusDays(30) }

    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }

    val state = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstVisibleWeekDate = selectedDate,
        firstDayOfWeek = firstDayOfWeek
    )

    Column {
        WeekCalendar(
            modifier = Modifier
                .background(color = Black)
                .padding(horizontal = 20.dp, vertical = 5.dp),
            state = state,
            dayContent = { day ->
                Day(date = day.date, isSelected = selectedDate == day.date) { clicked ->
                    if (selectedDate != clicked) {
                        viewModel.setSelectedDate(clicked)
                    }
                }
            }
        )

        AnimatedContent(
            targetState = dateState.hasTasks,
            label = "",
        ) { targetState ->
            when (targetState) {
                true -> {
                    TasksListSection(listTasks, destinationsNavigator)
                }
                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(340.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.nothing),
                            contentDescription = null,
                            modifier = Modifier.size(145.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Column(
                           horizontalAlignment = Alignment.CenterHorizontally,
                          verticalArrangement = Arrangement.spacedBy(9.dp)
                        ) {
                            Text(
                                text = "No Tasks",
                                fontSize = 17.sp,
                                fontFamily = priegoFont,
                                color = White,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "There are no specific tasks tied to this date.",
                                fontSize = 13.sp,
                                fontFamily = priegoFont,
                                color = White,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

private val dateFormatter = DateTimeFormatter.ofPattern("dd")

@Composable
fun Day(
    date: LocalDate,
    isSelected: Boolean,
    onClick: (LocalDate) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onClick(date) }
        ,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .width(45.dp)
                    .height(73.dp)
                    .background(
                        color = if (isSelected) White else Black,
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = date.dayOfWeek.toString().substring(0, 3).lowercase()
                            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                        fontSize = 14.sp,
                        color =  if (isSelected) Black else Color.Gray,
                        fontFamily = priegoFont,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = dateFormatter.format(date),
                        fontSize = 15.sp,
                        fontFamily = priegoFont,
                        color = if (isSelected) Black else White,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun TasksListSection(
    listTasks: List<Task>,
    destinationsNavigator: DestinationsNavigator
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 4.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        items(
            items = listTasks,
            key = { task ->
                task.id
            }
        ) {task ->
            when (task.priority) {
                "High" -> {
                    CardTask(
                        task = task,
                        onClick = {
                            destinationsNavigator.navigate(DetailsScreenDestination(task))
                        }
                    )
                }
                else -> {
                    CardTask(
                        task = task,
                        onClick = {
                            destinationsNavigator.navigate(DetailsScreenDestination(task))
                        },
                        containerColor = Grey,
                        primaryColor = White,
                        secondaryColor = White,
                        backgroundColor = GreyLight
                    )
                }
            }

        }
    }

}

@Composable
fun CardTask(
    task: Task,
    onClick: (Task) -> Unit,
    containerColor: Color = Blue,
    primaryColor: Color = Black,
    secondaryColor: Color = GreyLight,
    backgroundColor: Color = White
) {
    Card(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth()
            .height(210.dp)
            .padding(horizontal = 25.dp)
            .clickable {
                onClick(task)
            },
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        shape = RoundedCornerShape(15.dp)
    ) {

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(33.dp)
                        .background(backgroundColor, shape = RoundedCornerShape(18.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = task.priority,
                        fontSize = 12.sp,
                        color =  primaryColor,
                        fontFamily = priegoFont,
                        fontWeight = FontWeight.Medium
                    )
                }

                Box(
                    modifier = Modifier
                        .size(33.dp)
                        .background(backgroundColor, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = FeatherIcons.Share2,
                        contentDescription = null,
                        tint = primaryColor,
                        modifier = Modifier
                            .size(17.dp)
                            .clip(CircleShape)
                    )
                }
            }

            Text(
                text = task.title,
                fontSize = 20.sp,
                color =  primaryColor,
                fontFamily = priegoFont,
                fontWeight = FontWeight.Medium,
                maxLines = 2
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(7.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.calendar_icon),
                    contentDescription = null,
                    tint = primaryColor,
                    modifier = Modifier.size(16.dp)
                )

                Text(
                    text = DateTimeFormatter.ofPattern("dd MMM").format(task.dueDate),
                    fontSize = 13.sp,
                    color =  primaryColor,
                    fontFamily = priegoFont,
                    fontWeight = FontWeight.Medium
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_photo),
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 11.dp,
                        alignment = Alignment.End
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = FeatherIcons.MessageCircle,
                            contentDescription = null,
                            tint = secondaryColor,
                            modifier = Modifier.size(17.dp)
                        )
                        Text(
                            text = "4",
                            fontSize = 12.sp,
                            color = secondaryColor,
                            fontFamily = priegoFont,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = FeatherIcons.Paperclip,
                            contentDescription = null,
                            tint = secondaryColor,
                            modifier = Modifier.size(17.dp)
                        )
                        Text(
                            text = "16",
                            fontSize = 12.sp,
                            color = secondaryColor,
                            fontFamily = priegoFont,
                            fontWeight = FontWeight.Medium
                        )
                    }

                }

            }
        }

    }
}