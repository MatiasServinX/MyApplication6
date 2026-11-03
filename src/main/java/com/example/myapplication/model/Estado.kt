package com.example.myapplication.model

sealed interface Estado {
    val etiqueta: String

    data object Pendiente : Estado {
        override val etiqueta: String = "Pendiente"
    }

    data object Completado : Estado {
        override val etiqueta: String = "Completado"
    }

    data object Archivado : Estado {
        override val etiqueta: String = "Archivado"
    }
}
