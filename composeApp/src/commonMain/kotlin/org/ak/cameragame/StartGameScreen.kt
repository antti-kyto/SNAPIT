package org.ak.cameragame

import CreateGameScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import cameragame.composeapp.generated.resources.logo2
import org.jetbrains.compose.resources.painterResource

@Composable
fun StartGameScreen(
    onStartClicked: () -> Unit,
    gameWords: String,
    onGameWordsChanged: (String) -> Unit,
    useCustomGame: Boolean,
    onUseCustomGameChanged: (Boolean) -> Unit
) {

    var showSettings by remember { mutableStateOf(false) }
    val animation = rememberAnimations()

    Column(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showSettings) {
            CreateGameScreen(
                gameWords,
                onGameWordsChanged,
                useCustomGame,
                onUseCustomGameChanged,
                onShowSettingsChanged = { newValue -> showSettings = newValue })
        } else {
            Image(
                painter = painterResource(Res.drawable.logo2),
                contentDescription = null,
                modifier = Modifier.graphicsLayer(rotationZ = animation.rotation)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.graphicsLayer(
                    scaleX = animation.scale,
                    scaleY = animation.scale
                )
            ) {
                Button(
                    onClick = { onStartClicked() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Yellow,
                        contentColor = Color.Black
                    ),
                    contentPadding = PaddingValues(horizontal = 64.dp, vertical = 24.dp)
                ) {
                    Text(
                        text = "PLAY!",
                        fontSize = 44.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Get ready to move!",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                )
            }

            Spacer(modifier = Modifier.height(64.dp))

            Button(
                onClick = { showSettings = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Yellow,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "Settings",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}