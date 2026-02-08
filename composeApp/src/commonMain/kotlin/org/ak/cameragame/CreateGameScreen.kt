import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CreateGameScreen(
    gameWords: String,
    onGameWordsChanged: (String) -> Unit,
    useCustomGame: Boolean,
    onUseCustomGameChanged: (Boolean) -> Unit,
    onShowSettingsChanged: (Boolean) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize().safeContentPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Text(
            text = "Create Your Game",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp),
            color = Color.White,
        )

        // The Text Input Field
        OutlinedTextField(
            value = gameWords,
            onValueChange = { onGameWordsChanged(it) },
            label = { Text("Enter words (e.g. Apple, Banana, Cherry)") },
            placeholder = { Text("Type here...") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,

                // Border/Indicator colors
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White.copy(alpha = 0.5f),

                // Cursor color
                cursorColor = Color.White,

                // Label colors when focused
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(
                text = "Use custom game",
                color = Color.White,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.width(12.dp))
            Switch(
                checked = useCustomGame,
                onCheckedChange = { onUseCustomGameChanged(it) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Yellow,
                    checkedTrackColor = Color.Yellow.copy(alpha = 0.5f),
                    uncheckedThumbColor = Color.Gray,
                    uncheckedTrackColor = Color.White.copy(alpha = 0.2f)
                )
            )
        }

        Spacer(modifier = Modifier.height(64.dp))

        Button(
            onClick = { onShowSettingsChanged(false) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Yellow,
                contentColor = Color.Black
            ),
            contentPadding = PaddingValues(horizontal = 64.dp, vertical = 24.dp)
        ) {
            Text(
                text = "Back",
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}