package com.example.myapplication.ui.theme.state

import com.example.myapplication.model.Recordatorio

/**
 * Estados mutuamente excluyentes de la pantalla de listado.
 * Al usar sealed interface, el when de la UI debe contemplarlos a todos.
 */
sealed interface ListaUiState {
    data object Cargando : ListaUiState
    data object Vacio : ListaUiState
    data class ConDatos(val recordatorios: List<Recordatorio>) : ListaUiState
    data class Error(val mensaje: String) : ListaUiState
}
