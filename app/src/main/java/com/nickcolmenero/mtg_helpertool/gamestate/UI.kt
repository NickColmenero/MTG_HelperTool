package com.nickcolmenero.mtg_helpertool.gamestate

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nickcolmenero.mtg_helpertool.R

//@Preview(showBackground = true)
@Composable
fun TabletopTileLifeCounterPreview(navController: NavController) {
    val gameViewModel = remember {
        GameViewModel(application = Application()).apply {
            newGame(3, 40)
        }
    }
    TabletopTileLifeCounter(gameViewModel = gameViewModel, navController)
}

@Composable
fun TabletopTileLifeCounter(
    gameViewModel: GameViewModel,
    navController: NavController  // Add NavController parameter
) {
    val players by gameViewModel.players.collectAsState()
    val totalPlayers = players.size

    var expanded by remember { mutableStateOf(false) }
    var showColorPicker by remember { mutableStateOf(false) }
    var selectedPlayer by remember { mutableStateOf(0) }
    var showKeypad by remember { mutableStateOf(false) }
    var keypadTargetPlayer by remember { mutableStateOf(0) }
    var showPlayerSelectionForDungeon by remember { mutableStateOf(false) }

    fun playerTextRotation(index: Int): Float {
        return when (totalPlayers) {
            1 -> 0f
            2 -> when (index) {
                0 -> 90f
                else -> 270f
            }
            3 -> when (index) {
                0 -> 90f
                1 -> 270f
                else -> 0f
            }
            4 -> when (index) {
                0 -> 90f
                1 -> 270f
                2 -> 90f
                else -> 270f
            }
            else -> 0f
        }
    }

    Box(Modifier.fillMaxSize()) {
        when (totalPlayers) {
            1 -> {
                SingleTileTap(
                    playerName = players[0].name,
                    playerColor = players[0].color,
                    currentLife = players[0].life,
                    totalPlayers = totalPlayers,
                    isBottomThreePlayer = false,
                    rotation = playerTextRotation(0),
                    modifier = Modifier.fillMaxSize(),
                    onLifeChange = { delta -> gameViewModel.changePlayerLife(0, delta) },
                    onLongPress = { keypadTargetPlayer = 0; showKeypad = true }
                )
            }
            2 -> {
                Row(Modifier.fillMaxSize()) {
                    SingleTileTap(
                        playerName = players[0].name,
                        playerColor = players[0].color,
                        currentLife = players[0].life,
                        totalPlayers = totalPlayers,
                        isBottomThreePlayer = false,
                        rotation = playerTextRotation(0),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        onLifeChange = { delta -> gameViewModel.changePlayerLife(0, delta) },
                        onLongPress = { keypadTargetPlayer = 0; showKeypad = true }
                    )
                    SingleTileTap(
                        playerName = players[1].name,
                        playerColor = players[1].color,
                        currentLife = players[1].life,
                        totalPlayers = totalPlayers,
                        isBottomThreePlayer = false,
                        rotation = playerTextRotation(1),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        onLifeChange = { delta -> gameViewModel.changePlayerLife(1, delta) },
                        onLongPress = { keypadTargetPlayer = 1; showKeypad = true }
                    )
                }
            }
            3 -> {
                Column(Modifier.fillMaxSize()) {
                    Row(Modifier.weight(1f)) {
                        SingleTileTap(
                            playerName = players[0].name,
                            playerColor = players[0].color,
                            currentLife = players[0].life,
                            totalPlayers = totalPlayers,
                            isBottomThreePlayer = false,
                            rotation = playerTextRotation(0),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            onLifeChange = { delta -> gameViewModel.changePlayerLife(0, delta) },
                            onLongPress = { keypadTargetPlayer = 0; showKeypad = true }
                        )
                        SingleTileTap(
                            playerName = players[1].name,
                            playerColor = players[1].color,
                            currentLife = players[1].life,
                            totalPlayers = totalPlayers,
                            isBottomThreePlayer = false,
                            rotation = playerTextRotation(1),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            onLifeChange = { delta -> gameViewModel.changePlayerLife(1, delta) },
                            onLongPress = { keypadTargetPlayer = 1; showKeypad = true }
                        )
                    }
                    SingleTileTap(
                        playerName = players[2].name,
                        playerColor = players[2].color,
                        currentLife = players[2].life,
                        totalPlayers = totalPlayers,
                        isBottomThreePlayer = true,
                        rotation = playerTextRotation(2),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        onLifeChange = { delta -> gameViewModel.changePlayerLife(2, delta) },
                        onLongPress = { keypadTargetPlayer = 2; showKeypad = true }
                    )
                }
            }
            4 -> {
                Column(Modifier.fillMaxSize()) {
                    Row(Modifier.weight(1f)) {
                        SingleTileTap(
                            playerName = players[0].name,
                            playerColor = players[0].color,
                            currentLife = players[0].life,
                            totalPlayers = totalPlayers,
                            isBottomThreePlayer = false,
                            rotation = playerTextRotation(0),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            onLifeChange = { delta -> gameViewModel.changePlayerLife(0, delta) },
                            onLongPress = { keypadTargetPlayer = 0; showKeypad = true }
                        )
                        SingleTileTap(
                            playerName = players[1].name,
                            playerColor = players[1].color,
                            currentLife = players[1].life,
                            totalPlayers = totalPlayers,
                            isBottomThreePlayer = false,
                            rotation = playerTextRotation(1),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            onLifeChange = { delta -> gameViewModel.changePlayerLife(1, delta) },
                            onLongPress = { keypadTargetPlayer = 1; showKeypad = true }
                        )
                    }
                    Row(Modifier.weight(1f)) {
                        SingleTileTap(
                            playerName = players[2].name,
                            playerColor = players[2].color,
                            currentLife = players[2].life,
                            totalPlayers = totalPlayers,
                            isBottomThreePlayer = false,
                            rotation = playerTextRotation(2),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            onLifeChange = { delta -> gameViewModel.changePlayerLife(2, delta) },
                            onLongPress = { keypadTargetPlayer = 2; showKeypad = true }
                        )
                        SingleTileTap(
                            playerName = players[3].name,
                            playerColor = players[3].color,
                            currentLife = players[3].life,
                            totalPlayers = totalPlayers,
                            isBottomThreePlayer = false,
                            rotation = playerTextRotation(3),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            onLifeChange = { delta -> gameViewModel.changePlayerLife(3, delta) },
                            onLongPress = { keypadTargetPlayer = 3; showKeypad = true }
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            IconButton(

                modifier = Modifier.size(40.dp),
                onClick = { expanded = true }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "start game", tint = null)
            }
            Row() {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Color picker") },
                        onClick = {
                            expanded = false
                            showColorPicker = true
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("option") },
                        onClick = { expanded = false }
                    )
                    DropdownMenuItem(
                        text = { Text("Roll dice") },
                        onClick = { expanded = false }
                    )
                    DropdownMenuItem(
                        text = { Text("Flip coin") },
                        onClick = { expanded = false }
                    )
                    DropdownMenuItem(
                        text = { Text("Venture Into Dungeon") },
                        onClick = {
                            expanded = false
                            showPlayerSelectionForDungeon = true
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Reset Game") },
                        onClick = { expanded = false }
                    )
                    DropdownMenuItem(
                        text = { Text("Color Picker") },
                        onClick = {
                            expanded = false
                            showColorPicker = true
                        }
                    )
                }
            }
        }

        // Player selection dialog for dungeon venturing
        if (showPlayerSelectionForDungeon) {
            // Inside TabletopTileLifeCounter, fix the AlertDialog navigation:
            if (showPlayerSelectionForDungeon) {
                AlertDialog(
                    onDismissRequest = { showPlayerSelectionForDungeon = false },
                    title = { Text("Select Player to Venture") },
                    text = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            players.forEachIndexed { index, player ->
                                val isInDungeon = player.currDungeon != 0
                                Button(
                                    onClick = {
                                        showPlayerSelectionForDungeon = false
                                        if (isInDungeon) {
                                            // Fix: Add playerIndex parameter to the route
                                            navController.navigate("PlayerDungeon/${index}/${player.currDungeon}/${player.dungeonProgression}")
                                        } else {
                                            // Fix: Use the correct route name
                                            navController.navigate("player_dungeon_selection/$index")
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        "${player.name} - ${if (isInDungeon) "In Dungeon (Room ${player.dungeonProgression})" else "Not in Dungeon"}"
                                    )
                                }
                            }
                        }
                    },
                    confirmButton = {
                        Button(onClick = { showPlayerSelectionForDungeon = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }

        if (showKeypad) {
            FullScreenKeypad(
                playerIndex = keypadTargetPlayer,
                currentLife = players[keypadTargetPlayer].life,
                onLifeDelta = { delta ->
                    gameViewModel.changePlayerLife(
                        keypadTargetPlayer,
                        delta
                    )
                },
                onDismiss = { showKeypad = false }
            )
        }
    }

    if (showColorPicker) {
        var red by remember { mutableStateOf(128f) }
        var green by remember { mutableStateOf(128f) }
        var blue by remember { mutableStateOf(128f) }

        val selectedColor = Color(
            red = red.toInt(),
            green = green.toInt(),
            blue = blue.toInt()
        )

        AlertDialog(
            onDismissRequest = { showColorPicker = false },
            confirmButton = {
                Button(onClick = {
                    gameViewModel.setPlayerColor(selectedPlayer, selectedColor)
                    showColorPicker = false
                }) {
                    Text("Apply to Player ${selectedPlayer + 1}")
                }
            },
            dismissButton = {
                Button(onClick = { showColorPicker = false }) {
                    Text("Cancel")
                }
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Select Player")
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        players.forEachIndexed { index, _ ->
                            val isSelected = index == selectedPlayer
                            Button(
                                onClick = { selectedPlayer = index },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSelected)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.secondaryContainer
                                )
                            ) {
                                Text("P${index + 1}")
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .background(selectedColor, RoundedCornerShape(12.dp))
                    )
                    Text("Red: ${red.toInt()}")
                    Slider(
                        value = red,
                        onValueChange = { red = it },
                        valueRange = 0f..255f
                    )
                    Text("Green: ${green.toInt()}")
                    Slider(
                        value = green,
                        onValueChange = { green = it },
                        valueRange = 0f..255f
                    )
                    Text("Blue: ${blue.toInt()}")
                    Slider(
                        value = blue,
                        onValueChange = { blue = it },
                        valueRange = 0f..255f
                    )
                }
            }
        )
    }
}

@Composable
fun SingleTileTap(
    playerName: String,
    playerColor: Color,
    currentLife: Int,
    totalPlayers: Int,
    isBottomThreePlayer: Boolean,
    rotation: Float,
    modifier: Modifier,
    onLifeChange: (Int) -> Unit,
    onLongPress: () -> Unit
) {
    val contentColor = if (playerColor.luminance() < 0.5f) Color.White else Color.Black

    Box(modifier = modifier.padding(4.dp)) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { tapOffset ->
                            val width = size.width
                            val height = size.height
                            val rawDelta = when (rotation) {
                                90f -> if (tapOffset.x < width / 2) 1 else -1
                                270f -> if (tapOffset.x > width / 2) 1 else -1
                                0f -> if (tapOffset.y < height / 2) 1 else -1
                                180f -> if (tapOffset.y > height / 2) 1 else -1
                                else -> 1
                            }

                            // want to inverse it so it  is correct
                            val delta = -rawDelta

                            val finalDelta = if (isBottomThreePlayer) -delta else delta
                            onLifeChange(finalDelta)
                        },
                        onLongPress = { onLongPress() }
                    )
                },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = playerColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "",
                    color = contentColor,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.rotate(rotation)
                )
                Text(
                    text = currentLife.toString(),
                    color = contentColor,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.rotate(rotation)
                )
            }
        }
    }
}

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
                            text = if (sanitizedInput.isEmpty()) "0" else sanitizedInput,
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

@Composable
private fun KeypadRow(
    content: @Composable RowScope.() -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        content()
    }
}

@Composable
private fun KeypadButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surfaceVariant,
    contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val backgroundColor = if (isPressed) {
        color.copy(alpha = 0.7f)
    } else {
        color
    }

    Button(
        onClick = onClick,
        modifier = modifier
            .height(72.dp),
        interactionSource = interactionSource,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 2.dp
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = text,
            fontSize = when {
                text.length > 1 -> 24.sp
                text == "⌫" -> 32.sp
                else -> 36.sp
            },
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}