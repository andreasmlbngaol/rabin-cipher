package com.andreasmlbngaol.rabin.presentation.screen.rabin_basic.encrypt

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun RabinBasicEncryptScreen(
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<RabinBasicEncryptViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Enkripsi",
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
            value = state.messageAsString,
            onValueChange = viewModel::onMessageChange,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            label = { Text("Message") },
            isError = "message" in state.errors,
            supportingText = {
                if ("message" in state.errors) {
                    Text(state.errors["message"] ?: "")
                } else if (state.n != null) {
                    Text("Max: ${state.n!! - 1}")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            maxLines = 1
        )

        FilledTonalButton(
            onClick = viewModel::encrypt,
            enabled = state.isAllValid,
            shapes = ButtonDefaults.shapes(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Enkripsi")
        }

        EncryptResultCard(state.result, viewModel::resetResult)
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun EncryptResultCard(
    result: RabinBasicEncryptionResult?,
    onResetResult: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    AnimatedVisibility(result != null) {
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
                    // Header dengan Ciphertext dan View Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(32.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Hasil Enkripsi",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Ciphertext: ${res.ciphertext}",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                        FilledTonalButton(
                            onClick = { isExpanded = !isExpanded },
                            modifier = Modifier
                                .padding(top = 12.dp),
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
                                text = "Langkah-Langkah Enkripsi",
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

                            // Summary Box
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
                                    Text(
                                        text = "Public Key (n) = ${res.n}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "Private Key (p, q) = (${res.p}, ${res.q})",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "Message = ${res.message}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "Ciphertext (c) = ${res.ciphertext}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
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
