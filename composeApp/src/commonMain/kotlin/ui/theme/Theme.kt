package ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import cameragame.composeapp.generated.resources.Res
import cameragame.composeapp.generated.resources.*
import org.jetbrains.compose.resources.Font
import androidx.compose.material3.Typography

@Composable
fun AppTheme(
    content: @Composable() () -> Unit
) {
    val coiny = FontFamily(
        Font(Res.font.Coiny_Regular),
    )

    val defaultTypography = Typography()
    val typography = Typography(
        displayLarge = defaultTypography.displayLarge.copy(fontFamily = coiny),
        displayMedium = defaultTypography.displayMedium.copy(fontFamily = coiny),
        displaySmall = defaultTypography.displaySmall.copy(fontFamily = coiny),

        headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = coiny),
        headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = coiny),
        headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = coiny),

        titleLarge = defaultTypography.titleLarge.copy(fontFamily = coiny),
        titleMedium = defaultTypography.titleMedium.copy(fontFamily = coiny),
        titleSmall = defaultTypography.titleSmall.copy(fontFamily = coiny),

        bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = coiny),
        bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = coiny),
        bodySmall = defaultTypography.bodySmall.copy(fontFamily = coiny),

        labelLarge = defaultTypography.labelLarge.copy(fontFamily = coiny),
        labelMedium = defaultTypography.labelMedium.copy(fontFamily = coiny),
        labelSmall = defaultTypography.labelSmall.copy(fontFamily = coiny)
    )

    MaterialTheme(
        content = content,
        typography = typography
    )
}