package com.nickcolmenero.mtg_helpertool.ui_elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenKeypad(
    playerIndex: Int,
    currentLife: Int,
    onLifeDelta: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    var inputValue by rememberSaveable { mutableStateOf("") }
    var operation by rememberSaveable { mutableStateOf<Char?>(null) }
    val hapticFeedback = LocalHapticFeedback.current

    val sanitizedInput = inputValue.take(5)
    val numericValue = sanitizedInput.toIntOrNull() ?: 0
    val resultValue = when (operation) {
        '+' -> currentLife + numericValue
        '-' -> currentLife - numericValue
        else -> currentLife
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.97f))
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Player ${playerIndex + 1}",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    shape = RoundedCornerShape(32.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Current Life: $currentLife",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))


                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        val previewText = when {
                            operation == null -> "Select + or - to begin"
                            numericValue == 0 -> "Enter a value"
                            else -> {
                                val opSymbol = if (operation == '+') "+" else "−"
                                "$opSymbol $numericValue = $resultValue"
                            }
                        }

                        Text(
                            text = previewText,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )


                        Text(
                            text = sanitizedInput.ifEmpty { "0" },
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.displayMedium,
                            fontSize = 64.sp,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                KeypadRow {
                    KeypadButton(
                        "7",
                        onClick = {
                            inputValue += "7"; hapticFeedback.performHapticFeedback(
                            HapticFeedbackType.TextHandleMove
                        )
                        },
                        modifier = Modifier.weight(1f)
                    )
                    KeypadButton(
                        "8",
                        onClick = {
                            inputValue += "8"; hapticFeedback.performHapticFeedback(
                            HapticFeedbackType.TextHandleMove
                        )
                        }, modifier = Modifier.weight(1f)
                    )
                    KeypadButton(
                        "9",
                        onClick = {
                            inputValue += "9"; hapticFeedback.performHapticFeedback(
                            HapticFeedbackType.TextHandleMove
                        )
                        }, modifier = Modifier.weight(1f)
                    )
                }
                KeypadRow {
                    KeypadButton(
                        "4",
                        onClick = {
                            inputValue += "4"; hapticFeedback.performHapticFeedback(
                            HapticFeedbackType.TextHandleMove
                        )
                        }, modifier = Modifier.weight(1f)
                    )
                    KeypadButton(
                        "5",
                        onClick = {
                            inputValue += "5"; hapticFeedback.performHapticFeedback(
                            HapticFeedbackType.TextHandleMove
                        )
                        }, modifier = Modifier.weight(1f)
                    )
                    KeypadButton(
                        "6",
                        onClick = {
                            inputValue += "6"; hapticFeedback.performHapticFeedback(
                            HapticFeedbackType.TextHandleMove
                        )
                        }, modifier = Modifier.weight(1f)
                    )
                }

                KeypadRow {
                    KeypadButton(
                        "1",
                        onClick = {
                            inputValue += "1"; hapticFeedback.performHapticFeedback(
                            HapticFeedbackType.TextHandleMove
                        )
                        }, modifier = Modifier.weight(1f)
                    )
                    KeypadButton(
                        "2",
                        onClick = {
                            inputValue += "2"; hapticFeedback.performHapticFeedback(
                            HapticFeedbackType.TextHandleMove
                        )
                        }, modifier = Modifier.weight(1f)
                    )
                    KeypadButton(
                        "3",
                        onClick = {
                            inputValue += "3"; hapticFeedback.performHapticFeedback(
                            HapticFeedbackType.TextHandleMove
                        )
                        }, modifier = Modifier.weight(1f)
                    )
                }
                KeypadRow {
                    KeypadButton(
                        text = "C",
                        color = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer,
                        onClick = {
                            inputValue = ""
                            operation = null
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                        }, modifier = Modifier.weight(1f)
                    )
                    KeypadButton(
                        "0",
                        onClick = {
                            inputValue += "0"; hapticFeedback.performHapticFeedback(
                            HapticFeedbackType.TextHandleMove
                        )
                        }, modifier = Modifier.weight(1f)
                    )
                    KeypadButton(
                        text = "⌫",
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        onClick = {
                            if (inputValue.isNotEmpty()) {
                                inputValue = inputValue.dropLast(1)
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            }
                        }, modifier = Modifier.weight(1f)
                    )
                }

                KeypadRow {
                    KeypadButton(
                        text = "+",
                        color = if (operation == '+')
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.primaryContainer,
                        contentColor = if (operation == '+')
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onPrimaryContainer,
                        onClick = {
                            operation = '+'
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                        },
                        modifier = Modifier.weight(1f)
                    )
                    KeypadButton(
                        text = "−",
                        color = if (operation == '-')
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.primaryContainer,
                        contentColor = if (operation == '-')
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onPrimaryContainer,
                        onClick = {
                            operation = '-'
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                        },
                        modifier = Modifier.weight(1f)
                    )
                    KeypadButton(
                        text = "✓",
                        color = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary,
                        onClick = {
                            if (operation != null && numericValue > 0) {
                                val delta = if (operation == '+') numericValue else -numericValue
                                onLifeDelta(delta)
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.Confirm)
                                onDismiss()
                            } else if (operation != null && numericValue == 0) {
                                onDismiss()
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                onDismiss()
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            Text(
                text = "CANCEL",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )
        }
    }
}