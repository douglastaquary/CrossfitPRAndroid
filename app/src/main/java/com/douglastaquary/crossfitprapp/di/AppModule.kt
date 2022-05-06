package com.douglastaquary.crossfitprapp.di

import com.douglastaquary.crossfitprapp.screens.viewModel.TodayViewModel
import com.douglastaquary.crossfitprapp.ui.screens.viewModel.NewRecordViewModel
import org.koin.androidx.compose.get
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


val appModule = module {
    viewModel { TodayViewModel(get()) }
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule())
    }

// called by iOS etc
fun initKoin() = initKoin {}

fun commonModule() = module {
    single { TodayViewModel(get()) }
    single { NewRecordViewModel(get()) }

}
