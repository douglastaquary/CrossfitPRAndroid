package com.douglastaquary.crossfitprapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun SettingsScreen() {
    Scaffold(topBar = { SettingsTopBar() })
    {

        SettingItemView()
    }

}

@Composable
fun SettingsTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Settings",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    )
}

@ExperimentalComposeUiApi
@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}

@Composable
fun SettingItemView() {
    Card(elevation = 12.dp, shape = RoundedCornerShape(4.dp), modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
            verticalAlignment = Alignment.Top) {
            val textStyle = MaterialTheme.typography.body1
            val switchState = remember {
                mutableStateOf(true)
            }
            Column {
                //Text(text = "Switch title", style = MaterialTheme.typography.body2)
                Spacer(modifier = Modifier.height(8.dp))
                Row(Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)) {
                    Text(text = "Notification:", style = textStyle, textAlign = TextAlign.Justify, modifier = Modifier.fillMaxWidth())
                    Switch(
                        checked = switchState.value,
                        onCheckedChange = {switchState.value=it}
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingItemViewPreview() {
    SettingItemView()
}
