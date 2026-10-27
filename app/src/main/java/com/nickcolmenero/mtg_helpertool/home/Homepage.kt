package com.nickcolmenero.mtg_helpertool.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nickcolmenero.mtg_helpertool.R
import com.nickcolmenero.mtg_helpertool.ui_elements.NavigationDrawerContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Homepage(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawerContent(navController)
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("MTG Helper Tool", color = MaterialTheme.colorScheme.primary) },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            //Icon(
                                //imageVector = Icons.Default.Menu,
                                //contentDescription = "Menu"
                            //)
                        }
                    }
                )
            }
        ) { paddingValues ->
            Icon(painter = painterResource(id = R.drawable.menubackground), contentDescription = "Background Image", modifier = Modifier.fillMaxSize().fillMaxHeight(), tint = null)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                Text(
//                    text = "Welcome to MTG Life Counter",
//                    style = MaterialTheme.typography.headlineSmall,
//                    modifier = Modifier.padding(bottom = 32.dp)
//                )
                Spacer(modifier = Modifier.height(250.dp))
                IconButton(
                    onClick = { navController.navigate("setup_game") },
                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.startgame), contentDescription = "start game", tint = null)
                }

                Button(
                    onClick = { navController.navigate("game_screen") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Resume Game")
                }
            }
        }
    }
}