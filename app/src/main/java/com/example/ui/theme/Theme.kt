package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

private val GeometricLightColorScheme = lightColorScheme(
    primary = GeometricPrimary,
    secondary = GeometricSecondary,
    tertiary = GeometricTertiary,
    background = GeometricBackground,
    surface = GeometricSurface,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    onSecondary = GeometricPrimary,
    onTertiary = GeometricPrimary,
    onBackground = GeometricText,
    onSurface = GeometricText,
    outline = GeometricOutline
)

private val GeometricShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Disable dynamic color to maintain geometric theme brand
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) {
        // For simplicity, we use the same palette for dark theme as requested specifically for Geometric Balance
        // but typically we'd adapt it. For now, we adhere to the specific HTML design.
        GeometricLightColorScheme 
    } else {
        GeometricLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = GeometricShapes,
        content = content
    )
}
