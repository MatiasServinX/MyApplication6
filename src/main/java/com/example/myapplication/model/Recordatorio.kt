
package com.example.myapplication.model

import java.time.LocalDate

data class Recordatorio(
    val id: String,
    val titulo: String,
    val descripcion: String? = null,
    val categoria: Categoria,
    val prioridad: Prioridad,
    val estado: Estado,
    val fechaLimite: LocalDate? = null
)
