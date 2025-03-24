package dev.alpherg.fileserver.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Blue80,
    onPrimary = Blue20,
    primaryContainer = Blue30,
    onPrimaryContainer = Blue90,
    inversePrimary = Blue40,
    secondary = Jade80,
    onSecondary = Jade20,
    secondaryContainer = Jade30,
    onSecondaryContainer = Jade90,
    tertiary = Nadeshiko80,
    onTertiary = Nadeshiko20,
    tertiaryContainer = Nadeshiko30,
    onTertiaryContainer = Nadeshiko90,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = Gray10,
    onBackground = Gray90,
    surface = Gray10,
    onSurface = Gray90,
    inverseSurface = Gray90,
    inverseOnSurface = Gray20,
    surfaceVariant = GrayVariant30,
    onSurfaceVariant = GrayVariant80,
    outline = GrayVariant60,
)

private val LightColorScheme = lightColorScheme(
    primary = Blue40,
    onPrimary = Color.White,
    primaryContainer = Blue90,
    onPrimaryContainer = Blue10,
    inversePrimary = Blue80,
    secondary = Jade40,
    onSecondary = Color.White,
    secondaryContainer = Jade90,
    onSecondaryContainer = Jade10,
    tertiary = Nadeshiko40,
    onTertiary = Color.White,
    tertiaryContainer = Nadeshiko90,
    onTertiaryContainer = Nadeshiko10,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = Gray99,
    onBackground = Gray10,
    surface = Gray99,
    onSurface = Gray10,
    inverseSurface = Gray20,
    inverseOnSurface = Gray95,
    surfaceVariant = GrayVariant90,
    onSurfaceVariant = GrayVariant30,
    outline = GrayVariant50,
)

@Composable
fun FileServerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = AppTypography,
        content = content
    )
}