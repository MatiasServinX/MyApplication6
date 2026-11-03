package com.example.myapplication.ui.theme.state

import com.example.myapplication.model.Recordatorio

/** Estados posibles al recuperar un registro por su identificador. */
sealed interface DetalleUiState {
    data object Cargando : DetalleUiState
    data class ConDatos(val recordatorio: Recordatorio) : DetalleUiState
    data object NoEncontrado : DetalleUiState
    data class Error(val mensaje: String) : DetalleUiState
}
