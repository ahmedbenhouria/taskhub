package com.task.hub.presentation.ui.screen.details

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.task.hub.R
import com.task.hub.data.local.Task
import com.task.hub.presentation.ui.theme.Black
import com.task.hub.presentation.ui.theme.Blue
import com.task.hub.presentation.ui.theme.Grey
import com.task.hub.presentation.ui.theme.White
import com.task.hub.presentation.ui.theme.priegoFont
import compose.icons.FeatherIcons
import compose.icons.feathericons.ChevronLeft
import compose.icons.feathericons.Edit3
import compose.icons.feathericons.Share2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@Composable
fun DetailsContent(
    paddingValues: PaddingValues,
    task: Task
) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(Black, RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
    ) {
        Column {
            TabRows(
                pagerContent = { title ->
                    when (title) {
                        "Overview" -> {
                            OverviewContent(task)
                        }
                        "Activity" -> {
                            Text(text = "Activity", color = White)
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun OverviewContent(
    task: Task
) {
    Column(
        verticalArrangement = Arrangement.Top
    ) {
        ExpandableText(
            text = task.description,
            style = TextStyle(
                fontFamily = priegoFont,
                color = White,
                lineHeight = 20.sp,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            ),
            showMoreText = "... read more",
            showMoreStyle = SpanStyle(
                fontFamily = priegoFont,
                color = Blue,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            ),
            showLessText = " read less",
            showLessStyle = SpanStyle(
                fontFamily = priegoFont,
                color = Blue,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )
        )

        Spacer(modifier = Modifier.height(25.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Subtasks",
                fontSize = 15.sp,
                color =  White,
                fontFamily = priegoFont,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .alpha(0.7f)
                    .align(Alignment.Start)
            )

            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue,
                    contentColor = Black
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(57.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.checkmark),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Text(
                        text = "Create a content plan for march",
                        fontSize = 16.2.sp,
                        fontFamily = priegoFont,
                        color = Black,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }

            }
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Black,
                    contentColor = White
                ),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Blue),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(57.dp)
            ) {
                Text(
                    text = "Add a subtask",
                    fontSize = 16.2.sp,
                    fontFamily = priegoFont,
                    color = White,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(25.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text(
                text = "Attachments",
                fontSize = 15.sp,
                color =  White,
                fontFamily = priegoFont,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .alpha(0.7f)
                    .align(Alignment.Start)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    Modifier
                        .padding(start = 2.dp)
                        .width(80.dp)
                        .height(82.dp)
                        .dashedBorder(1.dp, Color.Gray, 9.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.plus_icon),
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(26.dp)
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.shape1),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.width(80.dp).height(82.dp).clip(RoundedCornerShape(10.dp))
                )

                Image(
                    painter = painterResource(id = R.drawable.shape2),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.width(80.dp).height(82.dp).clip(RoundedCornerShape(10.dp))
                )
            }

        }
    }
}

fun Modifier.dashedBorder(strokeWidth: Dp, color: Color, cornerRadiusDp: Dp) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }
        val cornerRadiusPx = density.run { cornerRadiusDp.toPx() }

        this.then(
            Modifier.drawWithCache {
                onDrawBehind {
                    val stroke = Stroke(
                        width = strokeWidthPx,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    )

                    drawRoundRect(
                        color = color,
                        style = stroke,
                        cornerRadius = CornerRadius(cornerRadiusPx)
                    )
                }
            }
        )
    }
)

const val DEFAULT_MINIMUM_TEXT_LINE = 3

@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    fontStyle: FontStyle? = null,
    text: String,
    collapsedMaxLine: Int = DEFAULT_MINIMUM_TEXT_LINE,
    showMoreText: String = "... Show More",
    showMoreStyle: SpanStyle = SpanStyle(fontWeight = FontWeight.W500),
    showLessText: String = " Show Less",
    showLessStyle: SpanStyle = showMoreStyle,
    textAlign: TextAlign? = null
) {
    var isExpanded by remember { mutableStateOf(false) }
    var clickable by remember { mutableStateOf(false) }
    var lastCharIndex by remember { mutableIntStateOf(0) }
    Box(modifier = Modifier
        .clickable(clickable) {
            isExpanded = !isExpanded
        }
        .then(modifier)
    ) {
        Text(
            modifier = textModifier
                .fillMaxWidth()
                .animateContentSize(),
            text = buildAnnotatedString {
                if (clickable) {
                    if (isExpanded) {
                        append(text)
                        withStyle(style = showLessStyle) { append(showLessText) }
                    } else {
                        val adjustText = text.substring(startIndex = 0, endIndex = lastCharIndex)
                            .dropLast(showMoreText.length)
                            .dropLastWhile { Character.isWhitespace(it) || it == '.' }
                        append(adjustText)
                        withStyle(style = showMoreStyle) { append(showMoreText) }
                    }
                } else {
                    append(text)
                }
            },
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLine,
            fontStyle = fontStyle,
            onTextLayout = { textLayoutResult ->
                if (!isExpanded && textLayoutResult.hasVisualOverflow) {
                    clickable = true
                    lastCharIndex = textLayoutResult.getLineEnd(collapsedMaxLine - 1)
                }
            },
            style = style,
            textAlign = textAlign
        )
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
            .height(283.dp)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ClickableButton(
                modifier = Modifier.size(28.dp),
                imageVector = FeatherIcons.ChevronLeft,
            ) {
                destinationsNavigator.popBackStack()
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ClickableButton(
                    modifier = Modifier.size(22.dp),
                    imageVector = FeatherIcons.Share2,
                )

                ClickableButton(
                    modifier = Modifier.size(23.dp),
                    imageVector = FeatherIcons.Edit3,
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 28.dp, end = 15.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = task.title,
                fontSize = 24.sp,
                color =  Black,
                fontFamily = priegoFont,
                fontWeight = FontWeight.Medium
            )

            Row(
                modifier = Modifier
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
                        verticalArrangement = Arrangement.spacedBy(3.dp)
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
                            painter = painterResource(id = R.drawable.calendar_icon),
                            contentDescription = null,
                            tint = Black,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(3.dp)
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabRows(
    pagerContent: @Composable (String) -> Unit
) {
    val tabItems = listOf(
        TabItem(title = "Overview"),
        TabItem(title = "Activity")
    )

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    val pagerState = rememberPagerState {
        tabItems.size
    }

    val coroutineScope = rememberCoroutineScope()

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

                        coroutineScope.launch {
                            pagerState.animateScrollToPage(selectedTabIndex)
                        }
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

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 26.dp, horizontal = 2.dp),
            verticalAlignment = Alignment.Top
        ) {index ->
            pagerContent(tabItems[index].title)
        }
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }
}

@Composable
fun ClickableButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    color: Color = Grey,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Blue,
            contentColor = color
        ),
        modifier = Modifier
            .width(38.dp)
            .height(38.dp)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = modifier)
    }
}