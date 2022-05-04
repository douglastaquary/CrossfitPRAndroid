package com.douglastaquary.crossfitprapp.screens.viewModel

import androidx.lifecycle.*
import com.douglastaquary.crossfitprapp.CrossfitPRService
import com.douglastaquary.crossfitprapp.model.Record
import kotlinx.coroutines.flow.*
import java.util.*

class TodayViewModel(get: Any) : ViewModel() {

    //val state = MutableStateFlow<Record>(Record(prName = "", prValue = 0.0, percentage = 0, date = Date()))
    //val prs = state.flatMapLatest { fetchPRInfo() }

    fun fetchPRInfo(): List<Record> {
        return listOf(
            Record(prName = "Air Squat (AS)", prValue = 120.0, percentage = 60, date = Date()),
            Record(prName = "Snatch", prValue = 110.0, percentage = 40, date = Date()),
            Record(prName = "Front Squat", prValue = 90.0, percentage = 40, date = Date())
        )
    }
}