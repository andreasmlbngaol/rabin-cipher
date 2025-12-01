package com.andreasmlbngaol.rabin

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.andreasmlbngaol.rabin.di.initKoin

fun main() {
    initKoin()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Rabin",
            state = rememberWindowState(
                placement = WindowPlacement.Maximized
            ),
//            undecorated = true
//            resizable = false
        ) {
            App()
        }
    }
}