package com.task.management.presentation.ui.screens.add

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
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
import com.task.management.R
import com.task.management.presentation.utils.MultiSelector
import com.task.management.presentation.ui.theme.Black
import com.task.management.presentation.ui.theme.Grey
import com.task.management.presentation.ui.theme.GreyLight
import com.task.management.presentation.ui.theme.White
import com.task.management.presentation.ui.theme.priegoFont
import compose.icons.FeatherIcons
import compose.icons.feathericons.Calendar
import compose.icons.feathericons.ChevronDown
import compose.icons.feathericons.Clock
import compose.icons.feathericons.MinusCircle
import compose.icons.feathericons.PlusCircle
import compose.icons.feathericons.X
import java.time.format.DateTimeFormatter
import es.dmoral.toasty.Toasty

@Composable
fun CreateTaskContent(
    viewModel: TaskViewModel
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is TaskViewModel.ValidationEvent.Success -> {
                    Toasty.success(
                        context,
                        "Task is created successfully.",
                        Toast.LENGTH_SHORT,
                        true
                    ).show()

                    val listMembers = viewModel.taskState.selectedMembers.toMutableList()
                    Log.d("TASK:", listMembers.toString())

                }
                is TaskViewModel.ValidationEvent.Error -> {
                    val validationState = event.validationState
                    val errorMessage: String = when {
                        !validationState.isValidTitle && !validationState.isValidDescription && !validationState.isMemberSelected ->
                            "Fill in the task information correctly."
                        !validationState.isValidTitle && !validationState.isValidDescription ->
                            "Enter a valid title and description."
                        !validationState.isValidTitle && !validationState.isMemberSelected ->
                            "Enter a valid title and select at least one member."
                        !validationState.isValidTitle -> "Enter a valid title."
                        !validationState.isValidDescription -> "Enter a valid description."
                        !validationState.isValidDueDate -> "Choose a due date for today or a future date."
                        !validationState.isMemberSelected -> "Select at least one member."
                        else -> "Fill in the task information correctly."
                    }
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
            .padding(vertical = 38.dp)
            .verticalScroll(rememberScrollState())
    ) {
        val state = viewModel.taskState

        val color by animateColorAsState(
            targetValue = Grey,
            label = "color"
        )

        CustomTextField(
            modifier = Modifier.height(48.dp),
            label = "Title",
            value = state.title,
            onValueChanged = { viewModel.onEvent(TaskFormEvent.TitleChanged(it)) },
            hint = "Enter task title",
            colorState = color
        )

        CustomTextField(
            modifier = Modifier.height(150.dp),
            label = "Description",
            value = state.description,
            onValueChanged = { viewModel.onEvent(TaskFormEvent.DescriptionChanged(it)) },
            hint = "Enter task description",
            colorState = color,
            maxLines = 5
        )

        DateTimePickerSection(viewModel)

        PrioritySelectorSection(viewModel)

        DropDownMenuMembersSection(viewModel)
    }
}

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChanged: (String) -> Unit,
    hint: String,
    colorState: Color,
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
            .background(colorState, RoundedCornerShape(12.dp))
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
    viewModel: TaskViewModel
) {
    val state = viewModel.taskState

    val calendarState = rememberUseCaseState()

    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH
        ),
        selection = CalendarSelection.Date { date ->
            viewModel.onEvent(TaskFormEvent.DueDateChanged(date))
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
                    viewModel.onEvent(TaskFormEvent.EstimateTaskChanged(value.value))
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
                        imageVector = FeatherIcons.Calendar,
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier
                            .size(18.dp)
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
                            .size(18.dp)
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
    viewModel: TaskViewModel
) {
    val state = viewModel.taskState

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
                viewModel.onEvent(TaskFormEvent.PriorityChanged(option))
            },
            selectedColor = Black,
            unselectedColor = White,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )
    }
}

data class Member(val name: String, val image: Int)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DropDownMenuMembersSection(
    viewModel: TaskViewModel
) {
    val state = viewModel.taskState

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
                                    viewModel.onEvent(TaskFormEvent.MemberAdded(member))
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
                            viewModel.onEvent(TaskFormEvent.MemberRemoved(member))
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