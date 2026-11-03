package com.example.myapplication.data

import com.example.myapplication.model.Recordatorio
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class RecordatorioRepositoryEnMemoria(
    registrosIniciales: List<Recordatorio> = emptyList(),
    private val simularErrorCarga: Boolean = false,
    private val simularErrorGuardado: Boolean = false
) : RecordatorioRepository {

    private val mutex = Mutex()
    private val registros = registrosIniciales.toMutableList()

    override suspend fun obtenerTodos(): List<Recordatorio> {

        delay(850)

        if (simularErrorCarga) {
            throw IllegalStateException("No se pudieron cargar los recordatorios. Probá nuevamente.")
        }

        // Devolvemos una copia para no exponer la lista mutable interna.
        return mutex.withLock { registros.toList() }
    }

    override suspend fun obtenerPorId(id: String): Recordatorio? {
        delay(350)
        return mutex.withLock { registros.firstOrNull { it.id == id } }
    }

    override suspend fun guardar(recordatorio: Recordatorio) {
        // Simula una escritura que tarda; la interfaz puede seguir respondiendo.
        delay(1_000)

        if (simularErrorGuardado) {
            throw IllegalStateException("No se pudo guardar el recordatorio. Intentá nuevamente.")
        }

        mutex.withLock {
            // Si el ID ya existía, lo reemplaza; si no, agrega un nuevo elemento.
            registros.removeAll { it.id == recordatorio.id }
            registros.add(recordatorio)
        }
    }
}
