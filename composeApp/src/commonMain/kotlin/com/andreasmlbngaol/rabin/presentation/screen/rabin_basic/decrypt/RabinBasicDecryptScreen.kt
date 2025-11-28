package com.andreasmlbngaol.rabin.presentation.screen.rabin_basic.decrypt

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun RabinBasicDecryptScreen(
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<RabinBasicDecryptViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Dekripsi",
            style = MaterialTheme.typography.headlineMedium,
            textDecoration = TextDecoration.Underline
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = state.pAsString,
                onValueChange = viewModel::onPChange,
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.large,
                label = { Text("P") },
                isError = "p" in state.errors,
                supportingText = {
                    AnimatedVisibility("p" in state.errors) {
                        Text(state.errors["p"] ?: "")
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1
            )
            OutlinedTextField(
                value = state.qAsString,
                onValueChange = viewModel::onQChange,
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.large,
                label = { Text("Q") },
                isError = "q" in state.errors,
                supportingText = {
                    AnimatedVisibility("q" in state.errors) {
                        Text(state.errors["q"] ?: "")
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1
            )
        }

        OutlinedTextField(
            value = state.ciphertextAsString,
            onValueChange = viewModel::onCiphertextChange,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            label = { Text("Ciphertext") },
            isError = "ciphertext" in state.errors,
            supportingText = {
                if ("ciphertext" in state.errors) {
                    Text(state.errors["ciphertext"] ?: "")
                } else if (state.n != null) {
                    Text("Max: ${state.n!! - 1}")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            maxLines = 1
        )

        FilledTonalButton(
            onClick = viewModel::decrypt,
            enabled = state.isAllValid,
            shapes = ButtonDefaults.shapes(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Dekripsi")
        }

        DecryptResultCard(state.result, viewModel::resetResult)
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DecryptResultCard(
    result: RabinBasicDecryptionResult?,
    onResetResult: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    AnimatedVisibility(visible = result != null) {
        result?.let { res ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Header dengan Kandidat dan View Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(32.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Hasil Dekripsi",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Kandidat: ${res.candidates.joinToString(", ") { it.toString()}}",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                        FilledTonalButton(
                            onClick = { isExpanded = !isExpanded },
                            modifier = Modifier.padding(top = 12.dp),
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.9f),
                                contentColor = MaterialTheme.colorScheme.onSecondary
                            ),
                            shapes = ButtonDefaults.shapes()
                        ) {
                            Text(if (isExpanded) "Tutup Langkah" else "Lihat Langkah")
                        }
                    }

                    // Expandable Detail
                    AnimatedVisibility(
                        visible = isExpanded,
                        enter = expandVertically(),
                        exit = shrinkVertically()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            HorizontalDivider()

                            // Step-by-step
                            Text(
                                text = "Langkah-Langkah Dekripsi",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 8.dp)
                            )

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                                        MaterialTheme.shapes.medium
                                    ),
                                shape = MaterialTheme.shapes.medium,
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    res.steps.forEach { step ->
                                        Text(
                                            text = step,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.fillMaxWidth(),
                                            lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.2f
                                        )
                                    }
                                }
                            }

                            HorizontalDivider()

                            // Kandidat pesan
                            Text(
                                text = "Kandidat Pesan Asli",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold
                            )

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.4f),
                                        MaterialTheme.shapes.medium
                                    ),
                                shape = MaterialTheme.shapes.medium,
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.2f)
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    res.candidates.forEachIndexed { index, candidate ->
                                        Text(
                                            text = "M${index + 1} = $candidate",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }

                            HorizontalDivider()

                            // Warning
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = MaterialTheme.shapes.large,
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Warning,
                                            contentDescription = null,
                                            tint = Color.Yellow,
                                            modifier = Modifier
                                                .size(24.dp)
                                        )
                                        Text(
                                            text = "Kelemahan Rabin Cipher",
                                            style = MaterialTheme.typography.labelLarge,
                                            color = MaterialTheme.colorScheme.onErrorContainer,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Text(
                                        text = "Hanya 1 dari ke-4 kandidat adalah pesan asli, namun tidak ada cara untuk menentukan yang mana. " +
                                                "Penerima harus mengetahui format pesan yang benar. " +
                                                "Rabin-p merupakan salah satu algoritma yang dapat menyelesaikan kelemahan ini.",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onErrorContainer
                                    )
                                }
                            }
                        }
                    }

                    HorizontalDivider()

                    FilledTonalButton(
                        onClick = {
                            onResetResult()
                            isExpanded = false
                        },
                        modifier = Modifier.align(Alignment.End),
                        shapes = ButtonDefaults.shapes(),
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.9f),
                            contentColor = MaterialTheme.colorScheme.onTertiary
                        )
                    ) {
                        Text("Reset")
                    }
                }
            }
        }
    }
}
