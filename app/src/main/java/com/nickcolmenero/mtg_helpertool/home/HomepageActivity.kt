package com.nickcolmenero.mtg_helpertool.home


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.nickcolmenero.mtg_helpertool.MainActivity.MainApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomepageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        @Composable
        fun LifeCounterDarkTheme(content: @Composable () -> Unit) {
            val darkColors = darkColorScheme(
                primary = Color(0xFFBB86FC),
                onPrimary = Color.Black,
                secondary = Color(0xFF03DAC6),
                onSecondary = Color.Black,
                background = Color(0xFF121212),
                onBackground = Color.White,
                surface = Color(0xFF1E1E1E),
                onSurface = Color.White,
                error = Color(0xFFCF6679),
                onError = Color.Black
            )

            MaterialTheme(
                colorScheme = darkColors,
                shapes = Shapes()          // optional: use your shapes
            ) {
                content()
            }
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LifeCounterDarkTheme {
            MainApp()
                }
        }
    }
}
