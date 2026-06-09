package com.nickcolmenero.mtg_helpertool.dungeonLogic

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nickcolmenero.mtg_helpertool.R
import com.nickcolmenero.mtg_helpertool.gamestate.GameViewModel

@Composable
fun DungeonUI(
    gameViewModel: GameViewModel,
    playerIndex: Int,
    selectedDungeonID: Int,
    currentRoomID: Int,
    onExitDungeon: () -> Unit,
    onBackToGame: () -> Unit
) {
    val dungeon = dungeonList(selectedDungeonID)
    val players by gameViewModel.players.collectAsState()
    val currentPlayer = players.getOrNull(playerIndex)

    var selectedRoomID by remember(currentPlayer?.dungeonProgression) {
        mutableStateOf(currentPlayer?.dungeonProgression ?: currentRoomID)
    }

    // Add a flag to prevent multiple navigation calls
    var isNavigating by remember { mutableStateOf(false) }

    // Update local state when ViewModel changes
    LaunchedEffect(currentPlayer?.dungeonProgression) {
        currentPlayer?.dungeonProgression?.let { newRoom ->
            if (newRoom != selectedRoomID && newRoom != 0) {
                selectedRoomID = newRoom
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Player ${playerIndex + 1} - ${dungeon.DungeonName}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )

            // Back to Game button (preserves progress)
            Button(
                onClick = {
                    if (!isNavigating) {
                        isNavigating = true
                        onBackToGame()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("← Back to Game")
            }

            // Exit Dungeon button (clears progress)
            Button(
                onClick = {
                    if (!isNavigating) {
                        isNavigating = true
                        gameViewModel.updatePlayerRoom(playerIndex, 0, 0)
                        onExitDungeon()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Exit Dungeon")
            }
        }
        val roomsByRow = dungeon.roomsInsideDungeon.groupBy { it.roomRow }

        // Render rows dynamically
        roomsByRow.toSortedMap().forEach { (_, roomsInRow) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                roomsInRow.forEach { room ->
                    // Check if player can move to this room
                    val currentRoomObj = dungeon.roomsInsideDungeon.firstOrNull { it.RoomID == selectedRoomID }
                    val canMove = currentRoomObj?.roomCanMoveTo?.contains(room.RoomID) == true
                    val isCurrentRoom = room.RoomID == selectedRoomID

                    DungeonTile(
                        imageRes = room.roomImagelocation,
                        isSelected = false,
                        isCurrentRoom = isCurrentRoom,
                        onClick = {
                            if (canMove || isCurrentRoom) {
                                // Update the ViewModel
                                gameViewModel.updatePlayerRoom(playerIndex, selectedDungeonID, room.RoomID)
                                // Update local state
                                selectedRoomID = room.RoomID
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        isEnabled = canMove || isCurrentRoom
                    )
                }
            }
        }
    }
}
@Composable
fun DungeonTile(
    imageRes: Int,
    isSelected: Boolean,
    isCurrentRoom: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
                .alpha(
                    when {
                        isCurrentRoom -> 1f
                        isSelected -> 0.8f
                        isEnabled -> 0.5f
                        else -> 0.2f
                    }
                )
                .clickable(enabled = isEnabled, onClick = onClick),
            contentScale = ContentScale.FillBounds
        )

        // Add indicator for current room
        if (isCurrentRoom) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .border(
                        width = 4.dp,
                        color = Color.Green,
                        shape = RectangleShape
                    )
            )
        }
    }
}