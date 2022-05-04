package com.douglastaquary.crossfitprapp

import com.douglastaquary.crossfitprapp.model.Record
import java.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.koin.core.component.KoinComponent

@ExperimentalCoroutinesApi
class CrossfitPRService: KoinComponent {

    init { }

    fun pollPRUpdates(): Flow<List<Record>> = flow {
        while (true) {
            println("fetchPRs")
            val prs = fetchPRInfo()
            emit(prs)
            delay(POLL_INTERVAL)
        }
    }

    private suspend fun fetchPRInfo(): List<Record> {
        return listOf(
            Record(prName = "Air Squat (AS)", prValue = 120.0, percentage = 60, date = Date()),
            Record(prName = "Snatch", prValue = 110.0, percentage = 40, date = Date()),
            Record(prName = "Front Squat", prValue = 90.0, percentage = 40, date = Date())
        )
    }

    companion object {
        private const val POLL_INTERVAL = 10000L
    }
}