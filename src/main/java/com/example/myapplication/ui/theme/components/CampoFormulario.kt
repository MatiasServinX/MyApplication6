package com.example.myapplication.ui.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp


@Composable
fun CampoFormulario(
    valor: String,
    etiqueta: String,
    onValorChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorMensaje: String? = null,
    unaLinea: Boolean = true,
    maxCaracteres: Int? = null
) {
    OutlinedTextField(
        value = valor,
        onValueChange = { nuevoValor ->
            // No recortamos silenciosamente el texto: el ViewModel debe poder validar
            // y comunicar un error si la descripción supera el máximo permitido.
            onValorChange(nuevoValor)
        },
        label = { Text(etiqueta) },
        isError = errorMensaje != null,
        supportingText = {
            if (errorMensaje != null) {
                Text(errorMensaje)
            } else if (maxCaracteres != null) {
                Text("${valor.length}/$maxCaracteres caracteres")
            }
        },
        singleLine = unaLinea,
        modifier = modifier
            .fillMaxWidth()
            .semantics {
                // La semántica permite que tecnologías de asistencia anuncien el error.
                if (errorMensaje != null) error(errorMensaje)
            }
    )
}

/**
 * Selector genérico controlado. El estado de qué opción fue elegida pertenece al ViewModel;
 * remember conserva únicamente si el menú desplegable está abierto.
 */
@Composable
fun <T> SelectorFormulario(
    etiqueta: String,
    opciones: List<T>,
    seleccionado: T?,
    textoOpcion: (T) -> String,
    onSeleccionar: (T) -> Unit,
    modifier: Modifier = Modifier,
    errorMensaje: String? = null
) {
    var expandido by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box {
            OutlinedButton(
                onClick = { expandido = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics {
                        if (errorMensaje != null) error(errorMensaje)
                    }
            ) {
                Text(
                    text = if (seleccionado == null) {
                        "$etiqueta: seleccionar"
                    } else {
                        "$etiqueta: ${textoOpcion(seleccionado)}"
                    }
                )
            }

            DropdownMenu(
                expanded = expandido,
                onDismissRequest = { expandido = false }
            ) {
                opciones.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(textoOpcion(opcion)) },
                        onClick = {
                            onSeleccionar(opcion)
                            expandido = false
                        }
                    )
                }
            }
        }

        if (errorMensaje != null) {
            Text(
                text = errorMensaje,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
