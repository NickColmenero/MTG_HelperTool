package com.nickcolmenero.mtg_helpertool.gamestate

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.AndroidViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {
    // Handle potential NullPointerException when getSharedPreferences is called on an uninitialized Application context (e.g. in Previews)
    private val prefs = try {
        application.getSharedPreferences("game_state", Context.MODE_PRIVATE)
    } catch (e: Exception) {
        null
    }

    private val _startingLife = MutableStateFlow(20)
    val startingLife: StateFlow<Int> = _startingLife.asStateFlow()
    private val _startingDungeon = MutableStateFlow(0)
    val startingDungeon: StateFlow<Int> = _startingDungeon.asStateFlow()  // Was _startingLife.asStateFlow()
    private val _startingDungeonProgression = MutableStateFlow(1)
    val startingDungeonProgression: StateFlow<Int> = _startingDungeonProgression.asStateFlow()  // Was _startingLife.asStateFlow()

    // Use StateFlow for better performance and reactive UI
    private val _players = MutableStateFlow<List<Player>>(emptyList())
    val players: StateFlow<List<Player>> = _players.asStateFlow()

    init {
        loadGameState()
    }

    fun playerColors(index: Int): Color {
        return _players.value.getOrNull(index)?.color ?: Color.Gray
    }
    fun updatePlayerRoom(playerIndex: Int, dungeonID: Int, roomID: Int) {
        _players.update { currentPlayers ->
            currentPlayers.toMutableList().apply {
                if (playerIndex in indices) {
                    this[playerIndex] = this[playerIndex].copy(
                        currDungeon = dungeonID,
                        dungeonProgression = roomID
                    )
                }
            }
        }
        saveGameState()
    }

    fun setPlayerColor(index: Int, color: Color) {
        _players.update { currentPlayers ->
            currentPlayers.toMutableList().apply {
                if (index < size) {
                    this[index] = this[index].copy(color = color)
                }
            }
        }
        saveGameState()
    }

    fun newGame(numPlayers: Int, life: Int) {
        _startingLife.value = life
        val newPlayers = List(numPlayers) { index ->
            Player(
                id = index,
                life = life,
                name = "Player ${index + 1}",
                color = Color.Gray,
                currDungeon = 0,
                dungeonProgression = 0
            )
        }
        _players.value = newPlayers
        saveGameState()
    }

    fun changePlayerLife(playerIndex: Int, amount: Int) {
        _players.update { currentPlayers ->
            currentPlayers.toMutableList().apply {
                if (playerIndex < size) {
                    val currentLife = this[playerIndex].life
                    this[playerIndex] = this[playerIndex].copy(life = currentLife + amount)
                }
            }
        }
        saveGameState()
    }

    // Helper function to get player by index
    fun getPlayerLife(index: Int): Int {
        return _players.value.getOrNull(index)?.life ?: 0
    }

    // Helper function to get player name
    fun getPlayerName(index: Int): String {
        return _players.value.getOrNull(index)?.name ?: "Player ${index + 1}"
    }

    // Get total number of players
    fun getPlayerCount(): Int {
        return _players.value.size
    }

    private fun saveGameState() {
        // Use safe call to avoid crash if prefs is null (e.g. in Previews)
        prefs?.edit()?.apply {
            putInt("num_players", _players.value.size)
            putInt("starting_life", _startingLife.value)
            _players.value.forEachIndexed { index, player ->
                putInt("player_life_$index", player.life)
                putInt("player_color_$index", player.color.toArgb())
                putString("player_name_$index", player.name)
                putInt("player_currDungeon_$index", player.currDungeon)
                putInt("player_dungeonProgression_$index", player.dungeonProgression)
            }
            apply()
        }
    }

    private fun loadGameState() {
        // Use safe access to prefs and return early if null
        val p = prefs ?: return
        val numPlayers = p.getInt("num_players", 0)
        if (numPlayers > 0) {
            val loadedStartingLife = p.getInt("starting_life", 20)
            _startingLife.value = loadedStartingLife
            val loadedCurrDungeon = p.getInt("player_currDungeon_0", 0)  // Use index 0, not missing variable
            _startingDungeon.value = loadedCurrDungeon
            val loadedDungeonProgression = p.getInt("player_dungeonProgression_0", 1)
            _startingDungeonProgression.value = loadedDungeonProgression


            val loadedPlayers = mutableListOf<Player>()
            for (i in 0 until numPlayers) {
                val life = p.getInt("player_life_$i", loadedStartingLife)
                val color = Color(p.getInt("player_color_$i", Color.Gray.toArgb()))
                val name = p.getString("player_name_$i", "Player ${i + 1}") ?: "Player ${i + 1}"
                val currDungeon = p.getInt("player_currDungeon_$i", loadedCurrDungeon)
                val dungeonProgression = p.getInt("player_dungeonProgression_$i", loadedDungeonProgression)
                loadedPlayers.add(Player(id = i, life = life, name = name, color = color, currDungeon = currDungeon, dungeonProgression = dungeonProgression))
            }
            _players.value = loadedPlayers
        }
    }
}

// Data class to represent a player
data class Player(
    val id: Int,
    val life: Int,
    val name: String,
    val color: Color,
    val currDungeon: Int,
    val dungeonProgression:Int
)