package com.example.myapplication

import com.example.myapplication.model.Categoria
import com.example.myapplication.model.Estado
import com.example.myapplication.model.Prioridad
import com.example.myapplication.model.Recordatorio

val categorias: Set<Categoria> = setOf(
    Categoria("cat-1", "Personal", "#4CAF50"),
    Categoria("cat-2", "Trabajo", "#2196F3"),
    Categoria("cat-3", "Estudio", "#FF9800")
)
val colorPorCategoria: Map<String, String> = mapOf(
    "cat-1" to "#4CAF50",
    "cat-2" to "#2196F3",
    "cat-3" to "#FF9800"
)
val Recordatorios: List<Recordatorio> = listOf(
    Recordatorio(
        id = "rec-1",
        titulo = "Comprar pan",
        categoria = categorias.first { it.id == "cat-1" },
        prioridad = Prioridad.Alta,
        estado = Estado.Pendiente
    ),
    Recordatorio(
        id = "rec-2",
        titulo = "Comprar aceote",
        categoria = categorias.first { it.id == "cat-1" },
        prioridad = Prioridad.Baja,
        estado = Estado.Completado
    ),
    Recordatorio(
        id = "rec-3",
        titulo = "Comprar monitor",
        categoria = categorias.first { it.id == "cat-3" },
        prioridad = Prioridad.Alta,
        estado = Estado.Pendiente
    ),

    )
fun Recordatorio.porEstado(estado: Estado): Boolean = this.estado == estado

fun filtrarPorEstado(lista: List<Recordatorio>, estado: Estado): List<Recordatorio> =
    lista.filter { it.estado == estado }

fun filtrarPorPrioridad(lista: List<Recordatorio>, prioridad: Prioridad): List<Recordatorio> =
    lista.filter { it.prioridad == prioridad }
fun imprimirPrioridadAlta() {
    val urgentes = filtrarPorPrioridad(
        Recordatorios,
        Prioridad.Alta
    )

    urgentes.forEach { recordatorio ->
        println("${recordatorio.titulo} - ${recordatorio.prioridad}")
    }
}

fun imprimirPendientes() {
    val pendientes = filtrarPorEstado(
        Recordatorios,
        Estado.Pendiente
    )

    pendientes.forEach { recordatorio ->
        println("Título: ${recordatorio.titulo}")
        println("Categoría: ${recordatorio.categoria.nombre}")

        println("----------------------")
    }
}
// corregir visual prioridad y estado