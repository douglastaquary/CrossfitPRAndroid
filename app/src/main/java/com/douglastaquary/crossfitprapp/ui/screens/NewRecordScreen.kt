package com.douglastaquary.crossfitprapp.ui.screens

import androidx.compose.foundation.*
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.douglastaquary.crossfitprapp.model.Record
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import com.douglastaquary.crossfitprapp.R
import com.douglastaquary.crossfitprapp.screens.viewModel.TodayViewModel
import com.douglastaquary.crossfitprapp.ui.screens.viewModel.NewRecordViewModel
import org.koin.androidx.compose.getViewModel


@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun NewRecordScreen(popBack: () -> Unit) {
    val viewModel = getViewModel<NewRecordViewModel>()
    val selectedPRValue: Int by viewModel.selectedRecord.collectAsState(0)
    val selectedPercentValue: Int by viewModel.selectedPercentage.collectAsState(0)
    val numbers = viewModel.range
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                ) {
                    // TODO:
                    Row() {
                        IconButton(onClick = { popBack() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                        Title(text = "New Record")
                    }
                    dropDownMenu()
                    DetailProperty("PR percentage", "$selectedPercentValue %")
                    PRPercentageCard(list = listOf("0", "10", "20", "30", "40", "50", "60", "70", "80", "90", "100"), numbers[selectedPercentValue].toInt()) {
                        viewModel.onPercentsValueChanged(it)
                    }
                    DetailProperty("Personal record", "$selectedPRValue Lb")
                    PRPercentageCard(list = listOf("0", "5", "10", "15", "20", "30", "40", "50", "60"), selectedPRValue) {
                        viewModel.onPRValueChanged(it)
                    }
                }
            }
        }
    }
}


@Composable
private fun Title(
    text: String
) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 16.dp)) {
        Text(
            text = text,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DetailProperty(label: String, value: String) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Divider(modifier = Modifier.padding(bottom = 4.dp))
        Text(
            text = label,
            modifier = Modifier.height(24.dp),
            style = MaterialTheme.typography.body1,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
    }
}

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun dropDownMenu() {
    var expanded by remember { mutableStateOf(false) }
    val exercises = listOf("SQUAT", "BACK SQUAT", "BAR MUSCLE-UP", "BENCH PRESS", "BOX JUMP (BJ)", "BURPEE OVER THE BAR", "CHEST TO BAR (CTB/C2B)",
    "CLEAN", "CLEAN & JERK", "CLUSTER", "DEADLIFT", "FRONT SQUAT"
    )
    var selectedText by remember { mutableStateOf("") }
    var textfieldSize by remember { mutableStateOf(Size.Zero)}
    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    Column(Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        TextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                },
            label = { Text("Select exercise") },
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { expanded = !expanded })
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background
            )
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){ textfieldSize.width.toDp() })
        ) {
            exercises.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText = label
                    expanded = false
                }) {
                    Text(text = label)
                }
            }
        }
    }
}

@Composable
fun PRPercentageCard(list: List<String>, selectedValue: Int, onValueChange: (Int) -> Unit) {
    val item by remember(selectedValue, list) {
        derivedStateOf { list[selectedValue] }
    }
    Column(modifier = Modifier.padding(12.dp)) {
        //MeasurementView(item)
        SliderPercentage(
            list.map { it },
            onValueChange = { onValueChange(it) },
            value = selectedValue.toFloat()
        )
    }
}

@Composable
fun SliderPercentage(legends: List<String>, value: Float, onValueChange: (Int) -> Unit) {
    val (sliderValue, setSliderValue) = remember { mutableStateOf(value) }
    val drawPadding = with(LocalDensity.current) { 10.dp.toPx() }
    val textSize = with(LocalDensity.current) { 10.dp.toPx() }
    val lineHeightDp = 10.dp
    val lineHeightPx = with(LocalDensity.current) { lineHeightDp.toPx() }
    val canvasHeight = 50.dp
    val textPaint = android.graphics.Paint().apply {
        color = if (isSystemInDarkTheme()) 0xffffffff.toInt() else 0xffff47586B.toInt()
        textAlign = android.graphics.Paint.Align.CENTER
        this.textSize = textSize
    }
    Box(contentAlignment = Alignment.Center) {
        Canvas(
            modifier = Modifier
                .height(canvasHeight)
                .fillMaxWidth()
                .padding(
                    top = canvasHeight
                        .div(2)
                        .minus(lineHeightDp.div(2))
                )
        ) {
            val yStart = 0f
            val distance = (size.width.minus(2 * drawPadding)).div(legends.size.minus(1))
            legends.forEachIndexed { index, date ->
                drawLine(
                    color = Color.DarkGray,
                    start = Offset(x = drawPadding + index.times(distance), y = yStart),
                    end = Offset(x = drawPadding + index.times(distance), y = lineHeightPx)
                )
                if (index.rem(2) == 1) {
                    this.drawContext.canvas.nativeCanvas.drawText(
                        date,
                        drawPadding + index.times(distance),
                        size.height,
                        textPaint
                    )
                }
            }
        }
        Slider(
            modifier = Modifier
                .testTag("SLIDER_TAG")
                .fillMaxWidth(),
            value = sliderValue,
            valueRange = 0f..legends.size.minus(1).toFloat(),
            steps = legends.size.minus(2),
            colors = customSliderColors(),
            onValueChange = {
                setSliderValue(it)
                onValueChange(it.toInt())
            })
    }
}

@Composable
private fun customSliderColors(): SliderColors = SliderDefaults.colors(
    activeTickColor = Color.Transparent,
    inactiveTickColor = Color.Transparent,
    thumbColor = colorResource(id = R.color.green),
    disabledThumbColor = colorResource(id = R.color.green),
    activeTrackColor = colorResource(id = R.color.green)
)

//case butterfly = "BUTTERFLY"
//case c2b = "CHEST TO BAR (CTB/C2B)"
//case clean = "CLEAN"
//case cleanJerk = "CLEAN & JERK"
//case cluester = "CLUSTER"
//case deadlift = "DEADLIFT"
//case du = "DOUBLE UNDERS (DU)"
//case frontSquat = "FRONT SQUAT"
//case gto = "GROUND TO OVERHEAD (GTO)"
//case hspu = "HANDSTAND PUSH-UP (HSPU)"
//case handstandWalk = "HANDSTAND WALK"
//case hangSnatchClean = "HANG (SNATCH/CLEAN)"
//case jerk = "JERK"
//case kbs = "KETTLEBELL SWING (KBS)"
//case kipping = "KIPPING"
//case k2e = "KNEES TO ELBOWS (K2E)"
//case lounges = "LUNGES"
//case mobility = "MOBILITY"
//case muscleUp = "MUSCLE-UP (MU)"
//case ohs = "OVER HEAD SQUAT (OHS)"
//case pistolSquat = "PISTOL SQUAT"
//case powerSnatchClean = "POWER (SNATCH/CLEAN)"
//case pullUp = "PULL-UP"
//case pushJerk = "PUSH JERK"
//case pushPress = "PUSH-PRESS"
//case pushUp = "PUSH-UP"
//case rackPosition = "RACK POSITION"
//case ringDips = "RING DIPS"
//case ringRows = "RING ROWS"
//case ropeClimb = "ROPE CLIMB"
//case russianLunge = "RUSSIAN LUNGE"
//case shoulderPress = "SHOULDER PRESS"
//case sitUp = "SIT-UP"
//case snatch = "SNATCH"
//case strictPullUp = "STRICT PULL-UP"
//case sumoDeadLift = "SUMO DEADLIFT"
//case sumoDeadLiftHightPull = "SUMO DEADLIFT HIGH PULL (SDHP)"
//case thruster = "THRUSTER"
//case t2b = "TOES TO BAR (T2B)"
//case tgu = "TURKISH GET UP (TGU)"
//case walkingLunges = "WALKING LUNGES"
//case vSitUp = "V-SIT-UP"
//case wallBall = "WALL BALL (WB)"
//case empty = ""