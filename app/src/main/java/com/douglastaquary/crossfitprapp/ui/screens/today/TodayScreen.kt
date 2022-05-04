package com.douglastaquary.crossfitprapp.ui.screens.today

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.douglastaquary.crossfitprapp.screens.viewModel.TodayViewModel
import com.douglastaquary.crossfitprapp.model.Record
import java.text.SimpleDateFormat
import java.util.*
import org.koin.androidx.compose.getViewModel

//Text(text = stringResource(R.string.app_name), fontSize = 18.sp, color = MaterialTheme.colors.onBackground) }
@Composable
fun TodayScreen() {
    val todayViewModel = getViewModel<TodayViewModel>()
    val records = todayViewModel.fetchPRInfo()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Personal records", color = MaterialTheme.colors.onBackground, fontWeight = FontWeight.Bold, fontSize = 26.sp) }, backgroundColor = MaterialTheme.colors.background, modifier = Modifier.height(88.dp), elevation = 12.dp)
        }) { innerPadding ->
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(24.dp))
            LazyColumn(contentPadding = innerPadding) {
                items(records) { record ->
                    PRView(record)
                }
            }
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = { /* ... */ },
                // Uses ButtonDefaults.ContentPadding by default
                contentPadding = PaddingValues(
                    start = 124.dp,
                    top = 16.dp,
                    end = 124.dp,
                    bottom = 16.dp
                )
            ) {
                // Inner content including an icon and a text label
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("New record")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodayScreenPreview() {
    TodayScreen()
}

@Composable
fun PRView(record: Record) {
    Card(elevation = 12.dp, shape = RoundedCornerShape(4.dp), modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
            verticalAlignment = Alignment.Top) {
            val textStyle = MaterialTheme.typography.body2
            Column {
                Text(text = record.prName, style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text(text = "${record.prValue} lb", style = textStyle, textAlign = TextAlign.Justify, modifier = Modifier.width(54.dp))
                    Spacer(modifier = Modifier.width(180.dp))
                }
                Column {
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "${record.date.dateToString("MMM d, yyyy")}", style = textStyle, maxLines = 1, textAlign = TextAlign.Right, modifier = Modifier.padding(start = 32.dp, top = 34.dp, end = 8.dp, bottom = 8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PRViewPreview() {
    PRView(record = Record(prName = "Air Squat (AS)", prValue = 120.0, percentage = 60, date = Date()))
}

fun Date.dateToString(format: String): String {
    //simple date formatter
    val dateFormatter = SimpleDateFormat(format, Locale.getDefault())

    //return the formatted date string
    return dateFormatter.format(this)
}