package com.andreasmlbngaol.rabin.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.andreasmlbngaol.rabin.presentation.component.Center
import com.andreasmlbngaol.rabin.presentation.component.TopBar
import com.andreasmlbngaol.rabin.presentation.component.TopBarType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToRabinBasic: () -> Unit,
    onNavigateToRabinP: () -> Unit,
    onNavigateToHRabin: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                "Rabin Cryptography",
                type = TopBarType.Centered,
                containerColor = Color.Transparent
            )
        }
    ) { paddingValues ->
        val padding = PaddingValues(
            start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
            top = 0.dp,
            end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
            bottom = paddingValues.calculateBottomPadding()
        )

        Center(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f),
                            MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.25f),
                            MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.25f)
                        )
                    )
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
            ) {
                Card(
                    onClick = onNavigateToRabinBasic,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f),
                    colors = CardDefaults.cardColors().copy(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Center(
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        Text(
                            text = "Classic Rabin",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }

                Card(
                    onClick = onNavigateToRabinP,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f),
                    colors = CardDefaults.cardColors().copy(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ) {
                    Center(
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        Text(
                            text = "Rabin-P",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }

                Card(
                    onClick = onNavigateToHRabin,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f),
                    colors = CardDefaults.cardColors().copy(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                ) {
                    Center(
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        Text(
                            text = "H-Rabin",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }

            }
        }
    }
}