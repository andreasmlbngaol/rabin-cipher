package com.andreasmlbngaol.rabin.presentation.navigation

import com.andreasmlbngaol.rabin.presentation.screen.home.HomeScreen
import com.andreasmlbngaol.rabin.presentation.screen.rabin_basic.RabinBasicScreen
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

@OptIn(KoinExperimentalAPI::class)
val navigationModule = module {
    single { Navigator(Screen.Home) }

    navigation<Screen.Home> {
        HomeScreen(
            onNavigateToRabinBasic = { get<Navigator>().navigateTo(Screen.RabinBasic) },
            onNavigateToRabinP = { get<Navigator>().navigateTo(Screen.RabinP) },
            onNavigateToHRabin = { get<Navigator>().navigateTo(Screen.HRabin) }
        )
    }

    navigation<Screen.RabinBasic> {
        RabinBasicScreen()
    }

    navigation<Screen.RabinP> {

    }
}