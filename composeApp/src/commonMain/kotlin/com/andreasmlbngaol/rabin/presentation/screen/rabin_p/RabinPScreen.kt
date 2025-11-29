package com.andreasmlbngaol.rabin.presentation.screen.rabin_p

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andreasmlbngaol.rabin.presentation.component.TopBar
import com.andreasmlbngaol.rabin.presentation.component.TopBarType
import com.andreasmlbngaol.rabin.presentation.screen.rabin_p.decrypt.RabinPDecryptScreen
import com.andreasmlbngaol.rabin.presentation.screen.rabin_p.encrypt.RabinPEncryptScreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun RabinPScreen() {
    Scaffold(
        topBar = {
            TopBar(
                title = "Rabin-p",
                type = TopBarType.Centered,
                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
            )
        }
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.08f)
                )
        ) {
            RabinPEncryptScreen(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )

            VerticalDivider(
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
                thickness = 2.dp
            )

            RabinPDecryptScreen(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
        }
    }
}
