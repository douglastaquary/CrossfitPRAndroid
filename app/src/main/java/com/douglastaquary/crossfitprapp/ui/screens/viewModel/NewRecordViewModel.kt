package com.douglastaquary.crossfitprapp.ui.screens.viewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class NewRecordViewModel(get: Any): ViewModel() {

    val selectedPercentage = MutableStateFlow<Int>(0)
    val range = listOf("10", "20", "30", "40", "50", "60", "70", "80", "90", "100")
    val selectedRecord = MutableStateFlow<Int>(0)
    val exercises = listOf("SQUAT", "BACK SQUAT", "BAR MUSCLE-UP", "BENCH PRESS", "BOX JUMP (BJ)", "BURPEE OVER THE BAR", "CHEST TO BAR (CTB/C2B)",
        "CLEAN", "CLEAN & JERK", "CLUSTER", "DEADLIFT", "FRONT SQUAT"
    )
    fun onPRValueChanged(selected: Int) {
        selectedRecord.value = selected
    }

    fun onPercentsValueChanged(selected: Int) {
        selectedPercentage.value = selected
    }
}
