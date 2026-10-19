package com.nickcolmenero.mtg_helpertool.gamestate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nickcolmenero.mtg_helpertool.R
import com.nickcolmenero.mtg_helpertool.ui_elements.FullScreenKeypad
import com.nickcolmenero.mtg_helpertool.ui_elements.SingleTileTap

@Composable
fun TabletopTileLifeCounter(
    gameViewModel: GameViewModel,
    navController: NavController
) {
    val players by gameViewModel.players.collectAsState()
    val totalPlayers = players.size

    var expanded by remember { mutableStateOf(false) }
    var showColorPicker by remember { mutableStateOf(false) }
    var selectedPlayer by remember { mutableStateOf(0) }
    var showKeypad by remember { mutableStateOf(false) }
    var keypadTargetPlayer by remember { mutableStateOf(0) }
    var showPlayerSelectionForDungeon by remember { mutableStateOf(false) }

    // All of this is for player orientation based off of how many players there are.
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
//                    DropdownMenuItem(
//                        text = { Text("Color picker") },
//                        onClick = {
//                            expanded = false
//                            showColorPicker = true
//                        }
//                    )
//                    DropdownMenuItem(
//                        text = { Text("option") },
//                        onClick = { expanded = false }
//                    )
//                    DropdownMenuItem(
//                        text = { Text("Roll dice") },
//                        onClick = { expanded = false }
//                    )
//                    DropdownMenuItem(
//                        text = { Text("Flip coin") },
//                        onClick = { expanded = false }
//                    )
                    DropdownMenuItem(
                        text = { Text("Venture Into Dungeon") },
                        onClick = {
                            expanded = false
                            showPlayerSelectionForDungeon = true
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Reset Game") },
                        onClick = { expanded = false
                            gameViewModel.newGame(totalPlayers, gameViewModel.startingLife.value)
                            navController.navigate("game_screen")
                        }
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
        if (showPlayerSelectionForDungeon) {
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
                Button(
                    onClick = {
                    gameViewModel.setPlayerColor(selectedPlayer, Color.Gray)

                },
                modifier = Modifier.fillMaxWidth()
                ) {Text("Reset color of Player ${selectedPlayer + 1}") }
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