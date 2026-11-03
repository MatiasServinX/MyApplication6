package com.example.myapplication.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.data.AppContainer
import com.example.myapplication.ui.theme.screens.DetalleScreen
import com.example.myapplication.ui.theme.screens.FormularioScreen
import com.example.myapplication.ui.theme.screens.ListaScreen
import com.example.myapplication.ui.theme.state.DetalleUiState
import com.example.myapplication.ui.theme.viewmodel.RecordatoriosViewModel
import com.example.myapplication.ui.theme.viewmodel.RecordatoriosViewModelFactory

/** Rutas centralizadas para evitar escribir cadenas distintas en cada pantalla. */
private object Rutas {
    const val LISTA = "lista"
    const val CREAR = "crear"
    const val DETALLE = "detalle/{id}"
    fun detalleConId(id: String) = "detalle/$id"
}

/**
 * Nivel superior de navegación. El ViewModel se crea fuera de los destinos para
 * que las tres pantallas compartan el mismo estado y el mismo repositorio.
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val viewModel: RecordatoriosViewModel = viewModel(
        factory = RecordatoriosViewModelFactory(AppContainer.repository)
    )

    NavHost(
        navController = navController,
        startDestination = Rutas.LISTA
    ) {
        composable(Rutas.LISTA) {
            val uiState by viewModel.listaUiState.collectAsStateWithLifecycle()

            ListaScreen(
                uiState = uiState,
                onCrearRecordatorio = {
                    viewModel.reiniciarFormulario()
                    navController.navigate(Rutas.CREAR)
                },
                onRecordatorioClick = { id ->
                    // Se transporta únicamente el ID; el detalle consulta el repositorio.
                    navController.navigate(Rutas.detalleConId(id))
                },
                onReintentar = viewModel::cargarLista
            )
        }

        composable(Rutas.CREAR) {
            val uiState by viewModel.formularioUiState.collectAsStateWithLifecycle()

            // El éxito es un evento de estado: al producirse, volvemos sin duplicar el listado.
            LaunchedEffect(uiState.guardadoExitoso) {
                if (uiState.guardadoExitoso) {
                    navController.popBackStack()
                    viewModel.reiniciarFormulario()
                }
            }

            FormularioScreen(
                uiState = uiState,
                categorias = viewModel.categoriasDisponibles,
                prioridades = viewModel.prioridadesDisponibles,
                onTituloChange = viewModel::cambiarTitulo,
                onDescripcionChange = viewModel::cambiarDescripcion,
                onCategoriaChange = viewModel::cambiarCategoria,
                onPrioridadChange = viewModel::cambiarPrioridad,
                onGuardar = viewModel::guardar,
                onCancelar = {
                    viewModel.reiniciarFormulario()
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Rutas.DETALLE,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id").orEmpty()
            val detalleState by viewModel.detalleUiState.collectAsStateWithLifecycle()

            LaunchedEffect(id) {
                if (id.isNotBlank()) {
                    viewModel.cargarDetalle(id)
                }
            }

            val estadoVisible = if (id.isBlank()) {
                DetalleUiState.Error("El identificador del recordatorio no es válido.")
            } else {
                detalleState
            }

            DetalleScreen(
                uiState = estadoVisible,
                onVolver = { navController.popBackStack() },
                onReintentar = { viewModel.cargarDetalle(id) }
            )
        }
    }
}
