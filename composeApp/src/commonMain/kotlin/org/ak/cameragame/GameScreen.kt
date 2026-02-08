package org.ak.cameragame

import ai.koog.agents.core.agent.AIAgent
import ai.koog.prompt.executor.clients.google.GoogleModels
import ai.koog.prompt.executor.llms.all.simpleGoogleAIExecutor
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import io.github.ismoy.imagepickerkmp.domain.config.ImagePickerConfig
import kotlinx.coroutines.delay
import io.github.ismoy.imagepickerkmp.domain.models.PhotoResult
import io.github.ismoy.imagepickerkmp.presentation.ui.components.ImagePickerLauncher
import kotlin.time.Clock


@Composable
fun GameScreen(
    nextGameState: () -> Unit,
    onImageToTakeChange: (String) -> Unit,
    onPhotoCaptured: (PhotoResult?) -> Unit,
    apiKey: String,
    gameWords: String,
    useCustomGame: Boolean
) {

    val animation = rememberAnimations()

    var millisLeft by remember { mutableStateOf(60_000L) }
    var resultText by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(true) }
    var showCamera by remember { mutableStateOf(false) }

    // 2. Decrease the delay to 10ms for smooth updates
    LaunchedEffect(key1 = Unit) {
        onPhotoCaptured(null)

        // Should consider to make a list of words and then randomly select or let AI choose on.
        val seed = Clock.System.now().epochSeconds.toString();

        var systemPrompt = ""
        if (useCustomGame && gameWords != "") {
            systemPrompt =
                "Select one random world from this list and return only that word: $gameWords"
        } else {
            systemPrompt = "You are a creative game master for an office scavenger hunt. " +
                    "First, brainstorm at least 50 possible items across tech gadgets, personal care items, safety equipment, kitchen tools, office supplies, and miscellaneous everyday objects. " +
                    "Then randomly select one item from your brainstormed list. " +
                    "Use the following random seed to influence your choice: $seed " +
                    "Return only a single common noun. No adjectives. No descriptions."
        }

        val agent = AIAgent(
            promptExecutor = simpleGoogleAIExecutor(apiKey),
            systemPrompt = systemPrompt,
            llmModel = GoogleModels.Gemini2_5Flash,
            temperature = 1.6
        )
        var result =
            agent.run("Return ONLY the idea and use one to three words, maximum of three words.")
        result = result.uppercase()

        onImageToTakeChange(result)
        resultText = result

        loading = false

        while (millisLeft > 0) {
            delay(10L)
            millisLeft -= 10
        }
        showCamera = false
        nextGameState()
    }

    if (showCamera) {
        ImagePickerLauncher(
            config = ImagePickerConfig(
                onPhotoCaptured = { result ->
                    onPhotoCaptured(result)
                    showCamera = false
                    nextGameState()
                },
                onError = { showCamera = false },
                onDismiss = { showCamera = false }
            )
        )
    }

    val minutes = (millisLeft / 60_000).toString()
    val seconds = ((millisLeft % 60_000) / 1000).toString().padStart(2, '0')

    val timeText = "$minutes:$seconds"

    Column(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (loading) {
            // Standard Material Spinner
            CircularProgressIndicator(color = Color.White)
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = "Loading...",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.graphicsLayer(
                    scaleX = animation.scale,
                    scaleY = animation.scale
                )
            )
        } else {
            Text(
                text = "Take a picture of...",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { showCamera = true },

                modifier = Modifier
                    .width(450.dp)  // Set a fixed width
                    .height(200.dp).graphicsLayer(
                        scaleX = animation.scale,
                        scaleY = animation.scale
                    ), // Set a fixed height
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Yellow,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = if (resultText == "") "Loading..." else resultText,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Tap to capture!",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = timeText,
                color = Color.White,
                fontSize = 64.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}