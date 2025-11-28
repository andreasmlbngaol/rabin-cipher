package com.andreasmlbngaol.rabin.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Home: NavKey

    @Serializable
    data object RabinBasic: NavKey

    @Serializable
    data object RabinP: NavKey

    @Serializable
    data object HRabin: NavKey
}