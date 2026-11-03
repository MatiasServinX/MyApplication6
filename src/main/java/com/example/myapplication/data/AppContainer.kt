package com.example.myapplication.data

import com.example.myapplication.model.Estado
import com.example.myapplication.model.Prioridad
import com.example.myapplication.model.Recordatorio
import java.time.LocalDate

object AppContainer {

    private fun crearRegistrosDeEjemplo(): List<Recordatorio> {
        val personal = CategoriasPredeterminadas.todas[0]
        val trabajo = CategoriasPredeterminadas.todas[1]
        val estudio = CategoriasPredeterminadas.todas[2]

        return listOf(
            Recordatorio(
                id = "rec-1",
                titulo = "Comprar aceite",
                descripcion = "Revisar también si falta yerba.",
                categoria = personal,
                prioridad = Prioridad.Baja,
                estado = Estado.Completado,
                fechaLimite = LocalDate.now().plusDays(1)
            ),
            Recordatorio(
                id = "rec-2",
                titulo = "Entregar trabajo práctico",
                descripcion = "Revisar el documento y subirlo al aula virtual.",
                categoria = trabajo,
                prioridad = Prioridad.Alta,
                estado = Estado.Pendiente,
                fechaLimite = LocalDate.now().plusDays(2)
            ),
            Recordatorio(
                id = "rec-3",
                titulo = "Estudiar Jetpack Compose",
                descripcion = "Repasar StateFlow, ViewModel y Navigation Compose.",
                categoria = estudio,
                prioridad = Prioridad.Media,
                estado = Estado.Pendiente,
                fechaLimite = null
            )
        )
    }

    private val registrosIniciales = when (DemoConfig.escenarioListado) {
        EscenarioListado.CON_DATOS -> crearRegistrosDeEjemplo()
        EscenarioListado.VACIO -> emptyList()
    }

    // Tipo declarado como interfaz: la UI/ViewModel no quedan atados a la clase en memoria.
    val repository: RecordatorioRepository = RecordatorioRepositoryEnMemoria(
        registrosIniciales = registrosIniciales,
        simularErrorCarga = DemoConfig.simularErrorCarga,
        simularErrorGuardado = DemoConfig.simularErrorGuardado
    )
}
