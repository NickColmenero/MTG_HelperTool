package com.nickcolmenero.mtg_helpertool.dungeonLogic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nickcolmenero.mtg_helpertool.gamestate.GameViewModel

@Composable
fun PlayerDungeonScreen(
    gameViewModel: GameViewModel,
    playerIndex: Int,
    navController: NavController
) {
    val players by gameViewModel.players.collectAsState()
    val player = players.getOrNull(playerIndex)

    // Check if player is already in a dungeon
    val isInDungeon = player?.currDungeon != null && player.currDungeon != 0

    if (!isInDungeon) {
        // Show dungeon selection for this specific player
        ChooseDungeonForPlayer(
            gameViewModel = gameViewModel,
            playerIndex = playerIndex,
            onDungeonSelected = { dungeonId ->
                // Initialize player at room 1 (or starting room) of the dungeon
                // First, find the starting room ID (smallest RoomID in the dungeon)
                val dungeon = dungeonList(dungeonId)
                val startingRoomId =
                    dungeon.roomsInsideDungeon.minByOrNull { it.RoomID }?.RoomID ?: 1

                // Save to ViewModel
                gameViewModel.updatePlayerRoom(playerIndex, dungeonId, startingRoomId)

                // Navigate to the dungeon with the starting room
                navController.navigate("PlayerDungeon/$playerIndex/$dungeonId/$startingRoomId")
            },
            onBack = { navController.navigate("game_screen") }
        )
    } else {
        // Navigate to current dungeon and room
        navController.navigate("PlayerDungeon/${playerIndex}/${player.currDungeon}/${player.dungeonProgression}")
    }
}

@Composable
fun ChooseDungeonForPlayer(
    gameViewModel: GameViewModel,
    playerIndex: Int,
    onDungeonSelected: (Int) -> Unit,
    onBack: () -> Unit
) {
    var selectedDungeon by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Button(onClick = onBack) {
                Text("← Back to Game")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Player ${playerIndex + 1}, Choose Your Dungeon",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = { selectedDungeon = 1 },
            modifier = Modifier.fillMaxWidth()
        ) { Text("The Undercity") }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { selectedDungeon = 2 },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Baldur's Gate Wilderness") }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { selectedDungeon = 3 },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Dungeon of the Mad Mage") }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { selectedDungeon = 4 },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Lost Mine of Phandelver") }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { selectedDungeon = 5 },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Tomb of Annihilation") }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (selectedDungeon != 0) {
                    onDungeonSelected(selectedDungeon)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = selectedDungeon != 0
        ) {
            Text(
                if (selectedDungeon != 0)
                    "Venture into ${dungeonList(selectedDungeon).DungeonName}"
                else
                    "Select a Dungeon"
            )
        }
    }
}