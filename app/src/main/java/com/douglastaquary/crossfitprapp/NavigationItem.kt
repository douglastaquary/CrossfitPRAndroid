package com.douglastaquary.crossfitprapp

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Today : NavigationItem("today", R.drawable.ic_today, "today")
    object Insights : NavigationItem("insights", R.drawable.ic_insights, "insights")
    object Settings : NavigationItem("settings", R.drawable.ic_settings, "settings")
}