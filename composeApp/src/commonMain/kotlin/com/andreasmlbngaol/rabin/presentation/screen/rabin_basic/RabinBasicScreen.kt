package com.andreasmlbngaol.rabin.presentation.screen.rabin_basic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.andreasmlbngaol.rabin.presentation.component.TopBar
import com.andreasmlbngaol.rabin.presentation.component.TopBarType
import com.andreasmlbngaol.rabin.presentation.screen.rabin_basic.decrypt.RabinBasicDecryptScreen
import com.andreasmlbngaol.rabin.presentation.screen.rabin_basic.encrypt.RabinBasicEncryptScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RabinBasicScreen() {
    Scaffold(
        topBar = {
            TopBar(
                title = "Rabin Cipher",
                type = TopBarType.Centered
            )
        }
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            RabinBasicEncryptScreen(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )

            VerticalDivider(color = MaterialTheme.colorScheme.outline)

            RabinBasicDecryptScreen(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
        }
    }
}