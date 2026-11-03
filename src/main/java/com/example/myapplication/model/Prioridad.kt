package com.example.myapplication.model

sealed interface Prioridad {
    val etiqueta: String

    data object Alta : Prioridad {
        override val etiqueta: String = "Alta"
    }

    data object Media : Prioridad {
        override val etiqueta: String = "Media"
    }

    data object Baja : Prioridad {
        override val etiqueta: String = "Baja"
    }
}
