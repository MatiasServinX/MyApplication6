package com.example.myapplication.data

/**
 * Interruptores sencillos para probar los distintos estados pedidos en la actividad.
 * Cambiá los valores y volvé a ejecutar la app para aplicar cada escenario.
 */
enum class EscenarioListado {
    CON_DATOS,
    VACIO
}

object DemoConfig {
    // CON_DATOS muestra tres ejemplos; VACIO permite probar la pantalla sin registros.
    var escenarioListado: EscenarioListado = EscenarioListado.CON_DATOS

    // Cambiar a true para que la carga inicial/reintento produzca un error simulado.
    var simularErrorCarga: Boolean = false

    // Cambiar a true para que Guardar devuelva un error simulado.
    var simularErrorGuardado: Boolean = false
}
