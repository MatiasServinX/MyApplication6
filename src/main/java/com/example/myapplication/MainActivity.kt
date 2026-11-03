package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.myapplication.ui.theme.navigation.AppNavigation
import com.example.myapplication.ui.theme.themes.RecordatoriosTheme

/** Activity de entrada: prepara la ventana y entrega el control a Compose. */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            RecordatoriosTheme {
                // El grafo mantiene el NavController y conecta los tres destinos.
                AppNavigation()
            }
        }
    }
}
