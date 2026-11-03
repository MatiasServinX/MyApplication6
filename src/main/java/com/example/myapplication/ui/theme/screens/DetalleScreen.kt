package com.example.myapplication.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.components.EstadoContenido
import com.example.myapplication.ui.theme.state.DetalleUiState

/** Presenta la información completa del registro recuperado por su ID. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleScreen(
    uiState: DetalleUiState,
    onVolver: () -> Unit,
    onReintentar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Detalle del recordatorio") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver al listado"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when (uiState) {
            is DetalleUiState.Cargando -> EstadoContenido(
                mensaje = "Buscando recordatorio...",
                cargando = true,
                modifier = Modifier.fillMaxSize().padding(innerPadding)
            )

            is DetalleUiState.NoEncontrado -> EstadoContenido(
                mensaje = "No se encontró un recordatorio con ese identificador.",
                textoAccion = "Volver",
                onAccion = onVolver,
                modifier = Modifier.fillMaxSize().padding(innerPadding)
            )

            is DetalleUiState.Error -> EstadoContenido(
                mensaje = uiState.mensaje,
                textoAccion = "Reintentar",
                onAccion = onReintentar,
                modifier = Modifier.fillMaxSize().padding(innerPadding)
            )

            is DetalleUiState.ConDatos -> {
                val recordatorio = uiState.recordatorio
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = recordatorio.titulo,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text("Descripción: ${recordatorio.descripcion ?: "Sin descripción"}")
                        Text("Categoría: ${recordatorio.categoria.nombre}")
                        Text("Prioridad: ${recordatorio.prioridad.etiqueta}")
                        Text("Estado: ${recordatorio.estado.etiqueta}")
                        Text("Fecha límite: ${recordatorio.fechaLimite ?: "Sin fecha"}")
                        Text("Identificador: ${recordatorio.id}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
