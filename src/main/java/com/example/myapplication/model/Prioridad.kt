package com.example.myapplication.model

sealed interface Prioridad {
    object Alta : Prioridad
    object Media : Prioridad
    object Baja : Prioridad
}