package org.ak.cameragame

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource

import cameragame.composeapp.generated.resources.Res
import io.github.ismoy.imagepickerkmp.domain.models.PhotoResult
import cameragame.composeapp.generated.resources.background

enum class GAMESTATE {
    START, PLAY, END
}

@Composable
@Preview
fun App() {
    MaterialTheme {

        var imageToTake by remember { mutableStateOf("") }
        var capturedPhoto by remember { mutableStateOf<PhotoResult?>(null) }
        var gameState by remember { mutableStateOf(GAMESTATE.START) }

        val apiKey = "APIKEY"

        fun nextGameState() {
            gameState = when (gameState) {
                GAMESTATE.START -> GAMESTATE.PLAY
                GAMESTATE.PLAY -> GAMESTATE.END
                GAMESTATE.END -> GAMESTATE.START
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(Res.drawable.background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // Use FillBounds if you want it to stretch exactly
            )

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                when (gameState) {
                    GAMESTATE.START -> StartGameScreen(onStartClicked = { nextGameState() })
                    GAMESTATE.PLAY -> GameScreen(
                        nextGameState = { nextGameState() },
                        onImageToTakeChange = { newPath ->
                            imageToTake = newPath
                        },
                        onPhotoCaptured = { result -> capturedPhoto = result },
                        apiKey
                    )

                    GAMESTATE.END -> EndScreen(
                        imageToTake,
                        capturedPhoto,
                        { nextGameState() },
                        apiKey
                    )
                }
            }
        }
    }
}