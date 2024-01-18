package org.bogdanspbm.pendulum

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.bogdanspbm.pendulum.enums.ENavigation
import org.bogdanspbm.pendulum.ui.theme.PendulumTheme
import org.bogdanspbm.pendulum.views.GameView
import org.bogdanspbm.pendulum.views.MainMenuView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            Game()
        }
    }
}

@Composable
fun Game() {
    val context = LocalContext.current
    var currentNavigation by remember { mutableStateOf(ENavigation.MENU) }
    val navController = rememberNavController()
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.menu) }
    mediaPlayer.start()
    mediaPlayer.setOnCompletionListener { mp ->
        mp.reset()
        mp.start()
    }


    PendulumTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(
                navController = navController,
                startDestination = currentNavigation.name,
            ) {
                composable(ENavigation.MENU.name) {
                    MainMenuView(
                        modifier = Modifier.fillMaxSize(),
                        navController = navController,
                        mediaPlayer = mediaPlayer
                    )
                }
                composable(ENavigation.GAME.name) {
                    GameView(
                        modifier = Modifier.fillMaxSize(),
                        navController = navController, mediaPlayer = mediaPlayer
                    )
                }
            }
        }
    }
}

