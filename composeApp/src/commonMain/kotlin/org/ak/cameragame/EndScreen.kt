package org.ak.cameragame

import ai.koog.prompt.dsl.prompt
import ai.koog.prompt.executor.clients.google.GoogleModels
import ai.koog.prompt.executor.llms.all.simpleGoogleAIExecutor
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cameragame.composeapp.generated.resources.Res
import cameragame.composeapp.generated.resources.lose
import cameragame.composeapp.generated.resources.win
import io.github.ismoy.imagepickerkmp.domain.models.PhotoResult
import kotlinx.coroutines.delay
import kotlinx.io.files.Path
import org.jetbrains.compose.resources.painterResource

@Composable
fun EndScreen(
    imageToTake: String,
    photoResult: PhotoResult?,
    nextGameState: () -> Unit,
    apiKey: String
) {

    val animation = rememberAnimations()

    val executor = simpleGoogleAIExecutor(apiKey)

    val prompt = prompt("images-prompt") {
        system("You are a game master. Player had to take a picture of a $imageToTake. Check the image and decide did the player succeed returning only true or false")
        user {
            attachments {
                val uri = photoResult?.uri ?: return@attachments
                val normalized = uri.removePrefix("file:")
                image(Path(normalized))
            }
        }
    }

    var winner by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = Unit) {

        if (photoResult != null) {
            val response = executor.execute(
                prompt = prompt,
                model = GoogleModels.Gemini2_5Flash,
                tools = emptyList()
            ).first()
            println(response.content)
            winner = response.content.toBoolean()
        }

        loading = false
    }

    LaunchedEffect(loading) {
        if (!loading) {
            delay(3500)
            nextGameState()
        }
    }

    val color =
        if (loading) Color.Transparent else if (winner) Color(0xFF1DC44A) else Color(0xFFC4311D)
    val image = if (winner) Res.drawable.win else Res.drawable.lose
    val text = if (winner) "Success!" else "Failed!"

    Column(
        modifier = Modifier
            .background(color)
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (loading) {
            // Standard Material Spinner
            CircularProgressIndicator(color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Just a sec...",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.graphicsLayer(
                    scaleX = animation.scale,
                    scaleY = animation.scale
                )
            )
        } else {
            Image(
                painter = painterResource(image),
                contentDescription = null
            )
            Text(
                text = text,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.graphicsLayer(
                    scaleX = animation.scale,
                    scaleY = animation.scale
                )
            )
        }
    }
}