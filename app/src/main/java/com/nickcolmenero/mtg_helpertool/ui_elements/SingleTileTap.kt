package com.nickcolmenero.mtg_helpertool.ui_elements

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

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