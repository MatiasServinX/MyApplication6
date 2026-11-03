package com.example.myapplication.ui.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.Recordatorio


@Composable
fun RecordatorioCard(
    recordatorio: Recordatorio,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(role = Role.Button, onClick = onClick)
            .semantics {
                contentDescription = "Abrir ${recordatorio.titulo}"
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = recordatorio.titulo,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Categoría: ${recordatorio.categoria.nombre}",
                style = MaterialTheme.typography.bodyMedium
            )

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "Prioridad: ${recordatorio.prioridad.etiqueta}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Estado: ${recordatorio.estado.etiqueta}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
