plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    // Java 17 es la versión requerida por AGP 9.x.
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    // El BOM alinea versiones compatibles entre las bibliotecas de Compose.
    implementation(platform("androidx.compose:compose-bom:2026.09.00"))

    implementation("androidx.activity:activity-compose:1.13.0")
    implementation("androidx.core:core-ktx:1.17.0")

    // Interfaz declarativa y componentes Material 3.
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")

    // Navegación entre listado, formulario y detalle.
    implementation("androidx.navigation:navigation-compose:2.10.2")

    // ViewModel y observación de StateFlow respetando el ciclo de vida.
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.11.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.11.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.11.0")

    // Corrutinas para operaciones simuladas sin bloquear la interfaz.
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
    implementation("com.github.skydoves:cloudy:1.0.0-alpha01")
    debugImplementation("androidx.compose.ui:ui-tooling")
}
