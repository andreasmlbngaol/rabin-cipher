package com.andreasmlbngaol.rabin.presentation.screen.h_rabin

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
import androidx.compose.ui.unit.dp
import com.andreasmlbngaol.rabin.presentation.component.TopBar
import com.andreasmlbngaol.rabin.presentation.component.TopBarType
import com.andreasmlbngaol.rabin.presentation.screen.h_rabin.decrypt.HRabinDecryptScreen
import com.andreasmlbngaol.rabin.presentation.screen.h_rabin.encrypt.HRabinEncryptScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HRabinScreen() {
    Scaffold(
        topBar = {
            TopBar(
                title = "H-Rabin",
                type = TopBarType.Centered,
                containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f)
            )
        }
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.08f)
                )
        ) {
            HRabinEncryptScreen(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )

            VerticalDivider(
                color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f),
                thickness = 2.dp
            )

            HRabinDecryptScreen(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
        }
    }
}
