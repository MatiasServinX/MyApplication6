package com.example.myapplication.data

import com.example.myapplication.model.Recordatorio
interface RecordatorioRepository {
    suspend fun obtenerTodos(): List<Recordatorio>
    suspend fun obtenerPorId(id: String): Recordatorio?
    suspend fun guardar(recordatorio: Recordatorio)
}
