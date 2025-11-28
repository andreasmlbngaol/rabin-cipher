package com.andreasmlbngaol.rabin.presentation.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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
                type = TopBarType.Centered
            )
        }
    ) { paddingValues ->
        Center(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                            text = "Rabin Basic",
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