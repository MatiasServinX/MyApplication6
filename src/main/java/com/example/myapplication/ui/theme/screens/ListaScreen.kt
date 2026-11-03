package com.example.myapplication.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.components.EstadoContenido
import com.example.myapplication.ui.theme.components.RecordatorioCard
import com.example.myapplication.ui.theme.state.ListaUiState

/** Pantalla principal: únicamente representa el UiState y comunica eventos mediante lambdas. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaScreen(
    uiState: ListaUiState,
    onCrearRecordatorio: () -> Unit,
    onRecordatorioClick: (String) -> Unit,
    onReintentar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Mis recordatorios") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCrearRecordatorio) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Crear recordatorio"
                )
            }
        }
    ) { innerPadding ->
        // when exhaustivo: cada estado excluyente tiene una representación propia.
        when (uiState) {
            is ListaUiState.Cargando -> {
                EstadoContenido(
                    mensaje = "Cargando recordatorios...",
                    cargando = true,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                )
            }

            is ListaUiState.Vacio -> {
                EstadoContenido(
                    mensaje = "Todavía no tenés recordatorios. Creá el primero para empezar.",
                    textoAccion = "Crear recordatorio",
                    onAccion = onCrearRecordatorio,
                    modifier = Modifier.fillMaxSize().padding(innerPadding)
                )
            }

            is ListaUiState.Error -> {
                EstadoContenido(
                    mensaje = uiState.mensaje,
                    textoAccion = "Reintentar",
                    onAccion = onReintentar,
                    modifier = Modifier.fillMaxSize().padding(innerPadding)
                )
            }

            is ListaUiState.ConDatos -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = uiState.recordatorios,
                        key = { recordatorio -> recordatorio.id }
                    ) { recordatorio ->
                        RecordatorioCard(
                            recordatorio = recordatorio,
                            onClick = { onRecordatorioClick(recordatorio.id) }
                        )
                    }
                }
            }
        }
    }
}
