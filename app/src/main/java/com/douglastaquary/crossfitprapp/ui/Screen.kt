package com.douglastaquary.crossfitprapp.ui

import com.douglastaquary.crossfitprapp.R

sealed class Screen(var route: String, var icon: Int, var title: String) {
    object Today : Screen("today", R.drawable.ic_today, "today")
    object Insights : Screen("insights", R.drawable.ic_insights, "insights")
    object Settings : Screen("settings", R.drawable.ic_settings, "settings")
    object NewRecord : Screen("new record", R.drawable.ic_today, "new record")
}