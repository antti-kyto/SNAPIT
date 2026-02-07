package org.ak.cameragame

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cameragame.composeapp.generated.resources.Res
import cameragame.composeapp.generated.resources.logo2
import org.jetbrains.compose.resources.painterResource

@Composable
fun StartGameScreen(onStartClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(Res.drawable.logo2),
            contentDescription = null
        )
        Button(
            onClick = { onStartClicked() },
            // Set the container (background) and content (text/icon) colors here
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
            color = Color.White,       // Sets the text color to white
            fontSize = 20.sp,          // Makes it bigger (import androidx.compose.ui.unit.sp)
            fontWeight = FontWeight.Medium // Optional: adds a bit of weight so it's readable
        )
    }
}