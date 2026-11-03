package com.example.myapplication.data

import com.example.myapplication.model.Categoria

/** Opciones válidas del selector de categorías del formulario. */
object CategoriasPredeterminadas {
    val todas: List<Categoria> = listOf(
        Categoria(id = "cat-1", nombre = "Personal", colorHex = "#4CAF50"),
        Categoria(id = "cat-2", nombre = "Trabajo", colorHex = "#2196F3"),
        Categoria(id = "cat-3", nombre = "Estudio", colorHex = "#FF9800")
    )
}
