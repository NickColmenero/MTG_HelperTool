package com.nickcolmenero.mtg_helpertool.ui_elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawerContent(navController: NavController) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxHeight()
            .fillMaxWidth(.5F)
            .padding(16.dp)
    ) {
        Text(text = "Navigation", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { navController.navigate("home") }) {
            Text("Home")
        }
        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { navController.navigate("LifeTracker") }) {
            Text("Life Tracker")
        }
        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { navController.navigate("Dungeons") }) {
            Text("Dungeons")
        }

        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { navController.navigate("SearchScreenNotDeckBuild") }) {
            Text("Search for cards")
        }
    }
}
