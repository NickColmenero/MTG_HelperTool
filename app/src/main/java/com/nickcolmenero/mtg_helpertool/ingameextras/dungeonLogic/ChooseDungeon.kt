package com.nickcolmenero.mtg_helpertool.ingameextras.dungeonLogic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nickcolmenero.mtg_helpertool.R


@Composable
fun ChooseDungeon(navController: NavController) {

    var selectedDungeon by remember { mutableStateOf(0) }

    Icon(
        painter = painterResource(id = R.drawable.menubackground),
        contentDescription = "Background Image",
        modifier = Modifier.fillMaxSize(),
        tint = null
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(250.dp))

        IconButton(
            onClick = { selectedDungeon = 7 },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.startgame),
                contentDescription = "Dungeon 7",
                tint = null
            )
        }

        Button(
            onClick = { selectedDungeon = 1 },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Dungeon 1") }

        Button(
            onClick = { selectedDungeon = 2 },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Dungeon 2") }
        Button(
            onClick = { selectedDungeon = 3 },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Dungeon 3") }
        Button(
            onClick = { selectedDungeon = 4 },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Dungeon 4") }
        Button(
            onClick = { selectedDungeon = 5 },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Dungeon 5") }

        Spacer(modifier = Modifier.height(16.dp))

        // Bottom button showing dungeon name
        Button(
            onClick = {
                if (selectedDungeon != 0) {
                    navController.navigate("DungeonUI/$selectedDungeon")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            val dungeonName = if (selectedDungeon != 0) {
                dungeonList(selectedDungeon).dungeonName // assuming dungeonList returns a Dungeon object with dungeonName
            } else {
                null
            }

            Text(
                if (dungeonName != null)
                    "Venture into $dungeonName"
                else
                    "Select a Dungeon"
            )
        }
    }
}