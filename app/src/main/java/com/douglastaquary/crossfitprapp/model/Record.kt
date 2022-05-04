package com.douglastaquary.crossfitprapp.model

import java.util.*

data class Record(
    val id: String? = "",
    val prName: String,
    val date: Date,
    val prValue: Double,
    val percentage: Int)