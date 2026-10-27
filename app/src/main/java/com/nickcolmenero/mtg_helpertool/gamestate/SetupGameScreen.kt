package com.nickcolmenero.mtg_helpertool.gamestate
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupGameScreen(navController: NavController, gameViewModel: GameViewModel) {
    var numPlayers by remember { mutableIntStateOf(2) }
    var selectedLife by remember { mutableIntStateOf(20) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Setup Game") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Player count selector
            Text("Select number of players:", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                for (i in 2..4) {
                    Button(
                        onClick = { numPlayers = i },
                        colors = if (numPlayers == i) ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                        else ButtonDefaults.buttonColors(),
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text("$i")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Starting life selector
            Text("Select starting life:", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                listOf(20, 40).forEach { life ->
                    Button(
                        onClick = { selectedLife = life },
                        colors = if (selectedLife == life) ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                        else ButtonDefaults.buttonColors(),
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text("$life")
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Start game button
            Button(
                onClick = {
                    gameViewModel.newGame(numPlayers, selectedLife)
                    navController.navigate("game_screen") {
                        popUpTo("setup_game") { inclusive = true } // Optional: remove setup from back stack
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Start Game")
            }
        }
    }
}