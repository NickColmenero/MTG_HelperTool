package com.nickcolmenero.mtg_helpertool

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nickcolmenero.mtg_helpertool.dungeonLogic.ChooseDungeonForPlayer
import com.nickcolmenero.mtg_helpertool.dungeonLogic.DungeonUI
import com.nickcolmenero.mtg_helpertool.dungeonLogic.PlayerDungeonScreen
import com.nickcolmenero.mtg_helpertool.dungeonLogic.dungeonList
import com.nickcolmenero.mtg_helpertool.gamestate.GameViewModel
import com.nickcolmenero.mtg_helpertool.gamestate.NewGameScreen
import com.nickcolmenero.mtg_helpertool.gamestate.ResumeGameScreen
import com.nickcolmenero.mtg_helpertool.gamestate.SetupGameScreen
import com.nickcolmenero.mtg_helpertool.gamestate.TabletopTileLifeCounter
import com.nickcolmenero.mtg_helpertool.home.Homepage

@Composable
fun MainApp() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val gameViewModel: GameViewModel = viewModel()
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") {
                Homepage(navController)
            }
            composable("setup_game") { SetupGameScreen(navController, gameViewModel) }
            composable("new_game") { NewGameScreen(navController, gameViewModel) }
            composable("resume_game") {
                ResumeGameScreen(navController, gameViewModel)
            }
            composable("game_screen") {
                TabletopTileLifeCounter(
                    gameViewModel = gameViewModel,
                    navController = navController
                )
            }

            // Make sure this route name matches exactly
            composable("player_dungeon_selection/{playerIndex}") { backStackEntry ->
                val playerIndex = backStackEntry.arguments?.getString("playerIndex")?.toInt() ?: 0
                ChooseDungeonForPlayer(
                    gameViewModel = gameViewModel,
                    playerIndex = playerIndex,
                    onDungeonSelected = { dungeonId ->
                        val dungeon = dungeonList(dungeonId)
                        val startingRoomId = dungeon.roomsInsideDungeon.minByOrNull { it.RoomID }?.RoomID ?: 1
                        gameViewModel.updatePlayerRoom(playerIndex, dungeonId, startingRoomId)
                        navController.navigate("PlayerDungeon/$playerIndex/$dungeonId/$startingRoomId")
                    },
                    onBack = {
                        navController.popBackStack("game_screen", false)
                    }
                )
            }

            // Make sure this route name matches exactly
            composable(
                "PlayerDungeon/{playerIndex}/{dungeonId}/{roomId}",
                arguments = listOf(
                    navArgument("playerIndex") { type = NavType.IntType },
                    navArgument("dungeonId") { type = NavType.IntType },
                    navArgument("roomId") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val playerIndex = backStackEntry.arguments?.getInt("playerIndex") ?: 0
                val dungeonId = backStackEntry.arguments?.getInt("dungeonId") ?: 1
                val roomId = backStackEntry.arguments?.getInt("roomId") ?: 1

                DungeonUI(
                    gameViewModel = gameViewModel,
                    playerIndex = playerIndex,
                    selectedDungeonID = dungeonId,
                    currentRoomID = roomId,
                    onExitDungeon = {
                        gameViewModel.updatePlayerRoom(playerIndex, 0, 0)
                        navController.popBackStack("game_screen", false)
                    },
                    onBackToGame = {
                        navController.popBackStack("game_screen", false) }
                )
            }
        }
    }
}