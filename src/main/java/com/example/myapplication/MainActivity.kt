
package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.Estado
import com.example.myapplication.model.Prioridad
import com.example.myapplication.model.Recordatorio
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    PantallaRecordatorios(
                        recordatorios = filtrarPorEstado(
                            Recordatorios,
                            Estado.Pendiente
                        ),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PantallaRecordatorios(
    recordatorios: List<Recordatorio>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Mis recordatorios pendientes",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Tenés ${recordatorios.size} recordatorios pendientes",
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
        )

        if (recordatorios.isEmpty()) {
            Text("¡No tenés tareas pendientes!")
        } else {
            LazyColumn(
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = recordatorios,
                    key = { it.id }
                ) { recordatorio ->
                    Card(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = recordatorio.titulo,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = "Categoría: ${recordatorio.categoria.nombre}"
                            )
                            Text(
                                text = "Prioridad: ${
                                    when (recordatorio.prioridad) {
                                        Prioridad.Alta -> "Alta"
                                        Prioridad.Media -> "Media"
                                        Prioridad.Baja -> "Baja"
                                    }
                                }"
                            )
                            Text(
                                text = "Estado: ${
                                    when (recordatorio.estado) {
                                        Estado.Completado -> "Completado"
                                        Estado.Pendiente -> "Pendiente"
                                        Estado.Archivado -> "Cancelado"
                                    }
                                }"
                            )

                        }
                    }
                }
            }
        }
    }
}

