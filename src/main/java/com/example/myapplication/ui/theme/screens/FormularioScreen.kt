package com.example.myapplication.ui.theme.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.Categoria
import com.example.myapplication.model.Prioridad
import com.example.myapplication.ui.theme.components.CampoFormulario
import com.example.myapplication.ui.theme.components.SelectorFormulario
import com.example.myapplication.ui.theme.state.FormularioUiState

/**
 * Formulario controlado: recibe los valores y lambdas. No conserva el título,
 * la categoría o la prioridad localmente; esos datos pertenecen al ViewModel.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioScreen(
    uiState: FormularioUiState,
    categorias: List<Categoria>,
    prioridades: List<Prioridad>,
    onTituloChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onCategoriaChange: (Categoria) -> Unit,
    onPrioridadChange: (Prioridad) -> Unit,
    onGuardar: () -> Unit,
    onCancelar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Crear recordatorio") },
                navigationIcon = {
                    IconButton(
                        onClick = onCancelar,
                        enabled = !uiState.enviando
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Cancelar y volver"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                // innerPadding evita que el formulario quede debajo de la TopAppBar.
                .padding(innerPadding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CampoFormulario(
                valor = uiState.titulo,
                etiqueta = "Título",
                onValorChange = onTituloChange,
                errorMensaje = uiState.errorTitulo,
                unaLinea = true
            )

            CampoFormulario(
                valor = uiState.descripcion,
                etiqueta = "Descripción",
                onValorChange = onDescripcionChange,
                errorMensaje = uiState.errorDescripcion,
                unaLinea = false,
                maxCaracteres = 200
            )

            SelectorFormulario(
                etiqueta = "Categoría",
                opciones = categorias,
                seleccionado = uiState.categoria,
                textoOpcion = { it.nombre },
                onSeleccionar = onCategoriaChange,
                errorMensaje = uiState.errorCategoria
            )

            SelectorFormulario(
                etiqueta = "Prioridad",
                opciones = prioridades,
                seleccionado = uiState.prioridad,
                textoOpcion = { it.etiqueta },
                onSeleccionar = onPrioridadChange,
                errorMensaje = uiState.errorPrioridad
            )

            val errorGeneral = uiState.errorGeneral
            if (errorGeneral != null) {
                Text(
                    text = errorGeneral,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.semantics {
                        error(errorGeneral)
                    }
                )
            }

            Button(
                onClick = onGuardar,
                enabled = uiState.esValido && !uiState.enviando,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.enviando) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(end = 8.dp),
                        strokeWidth = 2.dp
                    )
                    Text("Guardando...")
                } else {
                    Text("Guardar recordatorio")
                }
            }

            OutlinedButton(
                onClick = onCancelar,
                enabled = !uiState.enviando,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar")
            }
        }
    }
}
