package com.example.myapplication.ui.theme.state

import com.example.myapplication.model.Categoria
import com.example.myapplication.model.Prioridad

/**
 * Estado inmutable del formulario. Los valores y errores conviven en una data class
 * porque no son situaciones excluyentes: puede haber texto y un error a la vez.
 */
data class FormularioUiState(
    val titulo: String = "",
    val descripcion: String = "",
    val categoria: Categoria? = null,
    val prioridad: Prioridad? = null,
    val errorTitulo: String? = null,
    val errorDescripcion: String? = null,
    val errorCategoria: String? = null,
    val errorPrioridad: String? = null,
    val mostrarErrores: Boolean = false,
    val enviando: Boolean = false,
    val guardadoExitoso: Boolean = false,
    val errorGeneral: String? = null
) {
    // Estado derivado: no guardamos otra variable para habilitar/deshabilitar el botón.
    val esValido: Boolean
        get() = titulo.trim().length >= 4 &&
            descripcion.length <= 200 &&
            categoria != null &&
            prioridad != null
}
