package com.example.myapplication.ui.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EstadoContenido(
    mensaje: String,
    modifier: Modifier = Modifier,
    cargando: Boolean = false,
    textoAccion: String? = null,
    onAccion: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (cargando) {
            CircularProgressIndicator()
        }

        Text(
            text = mensaje,
            modifier = Modifier.padding(vertical = 16.dp),
            style = MaterialTheme.typography.bodyLarge
        )

        // El texto de la acción y su callback se entregan juntos para evitar botones inertes.
        if (textoAccion != null && onAccion != null) {
            Button(onClick = onAccion) {
                Text(text = textoAccion)
            }
        }
    }
}
