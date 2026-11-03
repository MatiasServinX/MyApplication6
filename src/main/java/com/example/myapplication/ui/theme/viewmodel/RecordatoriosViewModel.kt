package com.example.myapplication.ui.theme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.CategoriasPredeterminadas
import com.example.myapplication.data.RecordatorioRepository
import com.example.myapplication.model.Categoria
import com.example.myapplication.model.Estado
import com.example.myapplication.model.Prioridad
import com.example.myapplication.model.Recordatorio
import com.example.myapplication.ui.theme.state.DetalleUiState
import com.example.myapplication.ui.theme.state.FormularioUiState
import com.example.myapplication.ui.theme.state.ListaUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * ViewModel compartido por las pantallas del grafo.
 * Recibe una abstracción (RecordatorioRepository), no conoce Activity ni Views.
 */
class RecordatoriosViewModel(
    private val repository: RecordatorioRepository
) : ViewModel() {

    // Opciones válidas que usa el formulario. Se exponen como listas de solo lectura.
    val categoriasDisponibles: List<Categoria> = CategoriasPredeterminadas.todas
    val prioridadesDisponibles: List<Prioridad> = listOf(
        Prioridad.Alta,
        Prioridad.Media,
        Prioridad.Baja
    )

    // MutableStateFlow es privado: solamente el ViewModel puede modificarlo.
    private val _listaUiState = MutableStateFlow<ListaUiState>(ListaUiState.Cargando)
    val listaUiState: StateFlow<ListaUiState> = _listaUiState.asStateFlow()

    private val _formularioUiState = MutableStateFlow(
        validarFormulario(FormularioUiState())
    )
    val formularioUiState: StateFlow<FormularioUiState> = _formularioUiState.asStateFlow()

    private val _detalleUiState = MutableStateFlow<DetalleUiState>(DetalleUiState.Cargando)
    val detalleUiState: StateFlow<DetalleUiState> = _detalleUiState.asStateFlow()

    init {
        // La primera carga empieza al crear el ViewModel, no dentro de una función composable.
        cargarLista()
    }

    /** Obtiene todos los registros y transforma el resultado en un estado de pantalla. */
    fun cargarLista() {
        viewModelScope.launch {
            _listaUiState.value = ListaUiState.Cargando
            try {
                val registros = repository.obtenerTodos()
                _listaUiState.value = if (registros.isEmpty()) {
                    ListaUiState.Vacio
                } else {
                    ListaUiState.ConDatos(registros)
                }
            } catch (cancelacion: CancellationException) {
                // No convertimos la cancelación de la corrutina en un error de UI.
                throw cancelacion
            } catch (error: Exception) {
                _listaUiState.value = ListaUiState.Error(
                    error.message ?: "Ocurrió un error al cargar los recordatorios."
                )
            }
        }
    }

    /** Actualiza el título; si ya se intentó guardar, vuelve a validar en cada cambio. */
    fun cambiarTitulo(nuevoTitulo: String) {
        _formularioUiState.update { anterior ->
            val nuevo = anterior.copy(titulo = nuevoTitulo, errorGeneral = null)
            if (anterior.mostrarErrores) validarFormulario(nuevo) else nuevo
        }
    }

    /** La descripción admite hasta 200 caracteres, contando espacios. */
    fun cambiarDescripcion(nuevaDescripcion: String) {
        _formularioUiState.update { anterior ->
            val nuevo = anterior.copy(descripcion = nuevaDescripcion, errorGeneral = null)
            if (anterior.mostrarErrores) validarFormulario(nuevo) else nuevo
        }
    }

    /** Solo se aceptan categorías cuyo ID esté entre las opciones de la aplicación. */
    fun cambiarCategoria(nuevaCategoria: Categoria?) {
        val categoriaPermitida = nuevaCategoria?.let { candidata ->
            categoriasDisponibles.firstOrNull { it.id == candidata.id }
        }
        _formularioUiState.update { anterior ->
            val nuevo = anterior.copy(
                categoria = categoriaPermitida,
                errorGeneral = null
            )
            if (anterior.mostrarErrores) validarFormulario(nuevo) else nuevo
        }
    }

    /** La prioridad también debe pertenecer al conjunto de opciones permitidas. */
    fun cambiarPrioridad(nuevaPrioridad: Prioridad?) {
        val prioridadPermitida = nuevaPrioridad?.takeIf { it in prioridadesDisponibles }
        _formularioUiState.update { anterior ->
            val nuevo = anterior.copy(
                prioridad = prioridadPermitida,
                errorGeneral = null
            )
            if (anterior.mostrarErrores) validarFormulario(nuevo) else nuevo
        }
    }

    /**
     * Centraliza las reglas. El título se valida después de trim(); la descripción
     * no puede superar 200 caracteres; categoría y prioridad deben ser opciones reales.
     */
    private fun validarFormulario(estado: FormularioUiState): FormularioUiState {
        val categoriaValida = estado.categoria?.let { elegida ->
            categoriasDisponibles.any { it.id == elegida.id }
        } == true

        val prioridadValida = estado.prioridad?.let { it in prioridadesDisponibles } == true

        return estado.copy(
            mostrarErrores = true,
            errorTitulo = if (estado.titulo.trim().length < 4) {
                "El título debe tener al menos 4 caracteres, sin contar espacios externos."
            } else null,
            errorDescripcion = if (estado.descripcion.length > 200) {
                "La descripción no puede superar los 200 caracteres."
            } else null,
            errorCategoria = if (!categoriaValida) {
                "Seleccioná una categoría de la lista."
            } else null,
            errorPrioridad = if (!prioridadValida) {
                "Seleccioná una prioridad."
            } else null
        )
    }

    /**
     * Valida, marca el envío y llama al repositorio desde viewModelScope.
     * La UI no crea ni guarda directamente el objeto Recordatorio.
     */
    fun guardar() {
        val actual = _formularioUiState.value
        if (actual.enviando || actual.guardadoExitoso) return

        val validado = validarFormulario(actual)
        _formularioUiState.value = validado
        if (!validado.esValido) return

        val categoria = validado.categoria ?: return
        val prioridad = validado.prioridad ?: return

        val nuevoRecordatorio = Recordatorio(
            id = UUID.randomUUID().toString(),
            titulo = validado.titulo.trim(),
            descripcion = validado.descripcion.trim().takeIf { it.isNotEmpty() },
            categoria = categoria,
            prioridad = prioridad,
            estado = Estado.Pendiente,
            fechaLimite = null
        )

        // El estado cambia antes de iniciar el trabajo: evita doble toque en Guardar.
        _formularioUiState.update {
            it.copy(enviando = true, guardadoExitoso = false, errorGeneral = null)
        }

        viewModelScope.launch {
            try {
                repository.guardar(nuevoRecordatorio)
                _formularioUiState.update {
                    it.copy(enviando = false, guardadoExitoso = true)
                }
                // La lista se vuelve a consultar para que el nuevo registro aparezca al regresar.
                cargarLista()
            } catch (cancelacion: CancellationException) {
                throw cancelacion
            } catch (error: Exception) {
                _formularioUiState.update {
                    it.copy(
                        enviando = false,
                        guardadoExitoso = false,
                        errorGeneral = error.message ?: "No se pudo guardar el recordatorio."
                    )
                }
            }
        }
    }

    /** Limpia los valores cuando se entra a una creación nueva o se cancela. */
    fun reiniciarFormulario() {
        // Al abrir un formulario nuevo, los campos obligatorios ya quedan explicados.
        _formularioUiState.value = validarFormulario(FormularioUiState())
    }

    /** Busca el registro por ID; el objeto completo nunca viaja como argumento de navegación. */
    fun cargarDetalle(id: String) {
        viewModelScope.launch {
            _detalleUiState.value = DetalleUiState.Cargando
            try {
                val registro = repository.obtenerPorId(id)
                _detalleUiState.value = if (registro == null) {
                    DetalleUiState.NoEncontrado
                } else {
                    DetalleUiState.ConDatos(registro)
                }
            } catch (cancelacion: CancellationException) {
                throw cancelacion
            } catch (error: Exception) {
                _detalleUiState.value = DetalleUiState.Error(
                    error.message ?: "No se pudo recuperar el recordatorio."
                )
            }
        }
    }
}
