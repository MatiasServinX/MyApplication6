package com.example.myapplication.ui.theme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.RecordatorioRepository

/** Factory para construir el ViewModel con una implementación del repositorio. */
class RecordatoriosViewModelFactory(
    private val repository: RecordatorioRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecordatoriosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecordatoriosViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
    }
}
