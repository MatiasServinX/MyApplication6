package com.example.myapplication.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Superficie visual que imita el aspecto de un cristal.
// El fondo translúcido, el borde y el gradiente generan
// profundidad y reflejos.
@Composable
fun GlassSurface(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val forma = RoundedCornerShape(26.dp)

    Column(
        modifier = modifier
            .clip(forma)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.24f),
                        Color.White.copy(alpha = 0.08f)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.65f),
                        Color.White.copy(alpha = 0.12f)
                    )
                ),
                shape = forma
            )
            .padding(18.dp),
        content = content
    )
}