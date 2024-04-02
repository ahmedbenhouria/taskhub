package com.task.hub.presentation.ui.screen.add

import android.os.Parcelable
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.michaelflisar.composedialogs.core.DialogButtonType
import com.michaelflisar.composedialogs.core.DialogEvent
import com.michaelflisar.composedialogs.core.rememberDialogState
import com.michaelflisar.composedialogs.dialogs.input.DialogNumberPicker
import com.michaelflisar.composedialogs.dialogs.input.NumberPickerSetup
import com.michaelflisar.composedialogs.dialogs.input.rememberDialogNumber
import com.task.hub.R
import com.task.hub.data.local.Task
import com.task.hub.presentation.ui.screen.home.TaskViewModel
import com.task.hub.presentation.util.MultiSelector
import com.task.hub.presentation.ui.theme.Black
import com.task.hub.presentation.ui.theme.Grey
import com.task.hub.presentation.ui.theme.GreyLight
import com.task.hub.presentation.ui.theme.White
import com.task.hub.presentation.ui.theme.priegoFont
import compose.icons.FeatherIcons
import compose.icons.feathericons.ChevronDown
import compose.icons.feathericons.Clock
import compose.icons.feathericons.MinusCircle
import compose.icons.feathericons.PlusCircle
import compose.icons.feathericons.X
import java.time.format.DateTimeFormatter
import es.dmoral.toasty.Toasty
import kotlinx.parcelize.Parcelize

@Composable
fun CreateTaskContent(
    onHideSheet: (Boolean) -> Unit,
    addTaskViewModel: AddTaskViewModel = viewModel(),
    taskViewModel: TaskViewModel = hiltViewModel()
) {
    val state by addTaskViewModel.taskFormState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        addTaskViewModel.validationEvents.collect { event ->
            when (event) {
                is AddTaskViewModel.ValidationEvent.Success -> {
                    Toasty.success(
                        context,
                        "Task is created successfully.",
                        Toast.LENGTH_SHORT,
                        true
                    ).show()

                    val task = Task(
                        title = state.title,
                        description = state.description,
                        dueDate = state.dueDate,
                        estimateTime = state.estimateTask,
                        priority = state.selectedPriority,
                        members = state.selectedMembers.toList()
                    )

                    taskViewModel.insertTask(task)
                    onHideSheet(true)

                }
                is AddTaskViewModel.ValidationEvent.Error -> {
                    val errorMessage = event.errorMessage

                    Toasty.error(
                        context,
                        errorMessage,
                        Toast.LENGTH_SHORT,
                        true
                    ).show()
                }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 38.dp)
            .verticalScroll(rememberScrollState())
    ) {
        CustomTextField(
            modifier = Modifier.height(48.dp),
            label = "Title",
            value = state.title,
            onValueChanged = { addTaskViewModel.onEvent(TaskFormEvent.TitleChanged(it)) },
            hint = "Enter task title"
        )

        CustomTextField(
            modifier = Modifier.height(150.dp),
            label = "Description",
            value = state.description,
            onValueChanged = { addTaskViewModel.onEvent(TaskFormEvent.DescriptionChanged(it)) },
            hint = "Enter task description",
            maxLines = 5
        )

        DateTimePickerSection(state, addTaskViewModel::onEvent)

        PrioritySelectorSection(state, addTaskViewModel::onEvent)

        DropDownMenuMembersSection(state, addTaskViewModel::onEvent)
    }
}

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChanged: (String) -> Unit,
    hint: String,
    maxLines: Int = 1
) {
    val localFocusManager = LocalFocusManager.current

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(9.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 17.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color =  White,
            fontFamily = priegoFont,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.alpha(0.7f)
        )

        BasicTextField(modifier = Modifier
            .background(Grey, RoundedCornerShape(12.dp))
            .padding(3.dp)
            .fillMaxWidth()
            .then(modifier),
            value = value,
            onValueChange = onValueChanged,
            maxLines = maxLines,
            cursorBrush = SolidColor(Color.Gray),
            keyboardActions = KeyboardActions {
                localFocusManager.clearFocus()
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            textStyle = LocalTextStyle.current.copy(
                color =  White,
                fontFamily = priegoFont,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                textAlign = TextAlign.Start
            ),
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(vertical = 14.5.dp),
                    verticalAlignment = Alignment.Top,
                ) {
                    Box(
                        Modifier
                            .weight(1f)
                            .padding(horizontal = 13.dp)
                    ) {
                        if (value.isEmpty()) Text(
                            text = hint,
                            color = Color(0xFF6c6f77),
                            fontFamily = priegoFont,
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp
                        )
                        innerTextField()
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerSection(
    state: TaskFormState,
    onEvent: (TaskFormEvent) -> Unit
) {
    val calendarState = rememberUseCaseState()

    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH
        ),
        selection = CalendarSelection.Date { date ->
            onEvent(TaskFormEvent.DueDateChanged(date))
        }
    )

    val timeState = rememberDialogState()

    if (timeState.showing) {
        // special state for input dialog
        val value = rememberDialogNumber(number = state.estimateTask)

        // number dialog
        DialogNumberPicker(
            state = timeState,
            formatter = { "${it}h" },
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Task Duration Estimation",
                        fontSize = 16.sp,
                        color = Black,
                        fontFamily = priegoFont,
                        fontWeight = FontWeight.Medium
                    )
                }
            },
            value = value,
            textStyle = TextStyle(
                fontFamily = priegoFont,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Black
            ),
            icon = {
                Icon(
                    imageVector = FeatherIcons.Clock,
                    contentDescription = null,
                    tint = Black,
                    modifier = Modifier.size(23.dp)
                )
            },
            iconUp = {
                Icon(
                    imageVector = FeatherIcons.PlusCircle,
                    contentDescription = null,
                    tint = Black,
                    modifier = Modifier.size(23.dp)
                )
            },
            iconDown = {
                Icon(
                    imageVector = FeatherIcons.MinusCircle,
                    contentDescription = null,
                    tint = Black,
                    modifier = Modifier.size(23.dp)
                )
            },
            onEvent = {
                if (it is DialogEvent.Button && it.button == DialogButtonType.Positive) {
                    onEvent(TaskFormEvent.EstimateTaskChanged(value.value))
                }
            },
            setup = NumberPickerSetup(
                min = 1, max = 25, stepSize = 1
            )
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 17.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Text(
                text = "Due date",
                fontSize = 14.sp,
                color =  White,
                fontFamily = priegoFont,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .alpha(0.7f)
                    .padding(bottom = 9.dp)
            )

            Box(modifier = Modifier
                .background(Grey, RoundedCornerShape(12.dp))
                .padding(3.dp)
                .fillMaxWidth()
                .height(48.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = DateTimeFormatter
                            .ofPattern("dd MMM")
                            .format(state.dueDate),
                        color = White,
                        fontFamily = priegoFont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.calendar_icon),
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier
                            .size(20.dp)
                            .alpha(0.8f)
                            .clickable {
                                calendarState.show()
                            }
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Text(
                text = "Estimate task",
                fontSize = 14.sp,
                color =  White,
                fontFamily = priegoFont,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .alpha(0.7f)
                    .padding(bottom = 9.dp)
            )

            Box(modifier = Modifier
                .background(Grey, RoundedCornerShape(12.dp))
                .padding(3.dp)
                .fillMaxWidth()
                .height(48.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = state.estimateTask.toString() + "h",
                        color = White,
                        fontFamily = priegoFont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    )

                    Icon(
                        imageVector = FeatherIcons.Clock,
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier
                            .size(19.dp)
                            .alpha(0.8f)
                            .clickable {
                                timeState.show()
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun PrioritySelectorSection(
    state: TaskFormState,
    onEvent: (TaskFormEvent) -> Unit
) {
    val priorityOptions = listOf("Low", "Medium", "High")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 17.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Priority",
            fontSize = 14.sp,
            color =  White,
            fontFamily = priegoFont,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .alpha(0.7f)
                .padding(bottom = 9.dp)
        )

        MultiSelector(
            options = priorityOptions,
            selectedOption = state.selectedPriority,
            onOptionSelect = { option ->
                onEvent(TaskFormEvent.PriorityChanged(option))
            },
            selectedColor = Black,
            unselectedColor = White,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )
    }
}

@Parcelize
data class Member(val name: String, val image: Int): Parcelable

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DropDownMenuMembersSection(
    state: TaskFormState,
    onEvent: (TaskFormEvent) -> Unit
) {
    val members = listOf(
        Member(name = "Dianne Russell", image = R.drawable.member1),
        Member(name = "Floyd Wilson", image = R.drawable.profile_photo),
        Member(name = "Henderson", image = R.drawable.member2)
    )

    var expanded by remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 17.dp)
    ) {
        Text(
            text = "Members",
            fontSize = 14.sp,
            color =  White,
            fontFamily = priegoFont,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .alpha(0.7f)
                .padding(bottom = 9.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
            modifier = Modifier
        ) {
            BasicTextField(
                modifier = Modifier
                    .background(Grey, RoundedCornerShape(12.dp))
                    .padding(4.dp)
                    .fillMaxWidth()
                    .height(50.dp)
                    .menuAnchor(),
                value = "Select members",
                onValueChange = { },
                readOnly = true,
                textStyle = LocalTextStyle.current.copy(
                    color =  Color(0xFF6c6f77),
                    fontFamily = priegoFont,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start
                ),
                decorationBox = { innerTextField ->
                    Row(
                        Modifier
                            .fillMaxSize()
                            .padding(vertical = 14.5.dp),
                        verticalAlignment = Alignment.Top,
                    ) {
                        Box(
                            Modifier
                                .weight(1f)
                                .padding(horizontal = 13.dp)
                        ) {
                            Icon(
                                imageVector = FeatherIcons.ChevronDown,
                                contentDescription = null,
                                tint = White,
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.CenterEnd)
                            )
                            innerTextField()
                        }
                    }
                }
            )

            MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(15.dp))) {
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(Grey)
                ) {
                    members.forEach { member ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = member.name,
                                    color = White,
                                    fontFamily = priegoFont,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 15.sp,
                                    textAlign = TextAlign.Start
                                )
                            },
                            onClick = {
                                if (member !in state.selectedMembers) {
                                    onEvent(TaskFormEvent.MemberAdded(member))
                                }
                                expanded = false
                            },
                            modifier = Modifier.background(Grey)
                        )
                    }
                }
            }
        }

        if (state.selectedMembers.isNotEmpty()) {
            LazyRow(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.selectedMembers, key = { it.name }) {member ->
                    MemberItem(
                        modifier = Modifier.animateItemPlacement(),
                        member = member,
                        onClick = {
                            onEvent(TaskFormEvent.MemberRemoved(member))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MemberItem(
    modifier: Modifier = Modifier,
    member: Member,
    onClick: (Member) -> Unit
) {
    Box(modifier = Modifier
        .padding(vertical = 13.dp)
        .width(160.dp)
        .height(40.dp)
        .background(Black, RoundedCornerShape(23.dp))
        .border(1.dp, GreyLight, RoundedCornerShape(23.dp))
        .then(modifier)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            Image(
                painter = painterResource(id = member.image),
                contentDescription = null,
                modifier = Modifier
                    .width(30.dp)
                    .fillMaxHeight()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Text(
                text = member.name,
                color = White,
                fontFamily = priegoFont,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )

            Icon(
                imageVector = FeatherIcons.X,
                contentDescription = null,
                tint = White,
                modifier = Modifier
                    .padding(end = 5.dp)
                    .size(15.dp)
                    .clickable {
                        onClick(member)
                    }
            )
        }
    }
}