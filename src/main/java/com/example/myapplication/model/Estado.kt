package com.example.myapplication.model

sealed interface Estado {
    object Completado : Estado
    object Pendiente : Estado
    object Archivado : Estado}