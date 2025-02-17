# PressureDiary

PressureDiary es un proyecto desarrollado en **Kotlin Multiplatform (KMP)** que integra múltiples tecnologías y patrones modernos para ofrecer una solución escalable y mantenible.

* `/composeApp` es para el código que será compartido a través de tus aplicaciones Compose Multiplataforma.
  Contiene varias subcarpetas:
  - `commonMain` es para el código que es común para todos los objetivos.
  - Otras carpetas son para código Kotlin que se compilará sólo para la plataforma indicada en el nombre de la carpeta.
    Por ejemplo, si quieres usar CoreCrypto de Apple para la parte iOS de tu aplicación Kotlin,
    `iosMain` sería la carpeta adecuada para tales llamadas.

* `/iosApp` contiene las aplicaciones iOS. Incluso si estás compartiendo tu UI con Compose Multiplataforma,
  necesitas este punto de entrada para tu aplicación iOS. Aquí es también donde debes añadir el código SwiftUI para tu proyecto.

## Tecnologías Utilizadas

- **Kotlin Multiplatform (KMP):** Código compartido entre plataformas.
- **Ktor:** Comunicación con API remota.
- **Jetpack Compose Multiplatform:** Gestión de la UI en Android.
- **SQLDelight:** Gestión de la base de datos local.
- **Datastore:** Almacenamiento local de preferencias y datos sencillos.
- **Koin:** Inyección de dependencias.
- **Test Unitarios:** Pruebas automatizadas para asegurar la calidad del código.

## Arquitecturas

- **Clean Architecture:** Separación clara entre capas (dominio, datos y presentación).
- **MVVM (Model-View-ViewModel):** Organización de la UI en Android.
- **MVI (Model-View-Intent):** Gestión de estados e interacciones de la UI.

## Patrones de Diseño

- **SOLID:** Principios para un diseño orientado a objetos robusto.
- **KISS:** Keep It Simple, Stupid.
- **DRY:** Don't Repeat Yourself.

## Instalación y Ejecución en Android Studio

1. **Clonar el repositorio**

   ```bash
   git clone https://github.com/tu_usuario/PressureDiary.git
   cd PressureDiary
   ```

2. **Abrir el proyecto en Android Studio**
- Abre Android Studio.
- Selecciona File > Open y elige la carpeta del proyecto.

3. **Configurar el proyecto**
- Android Studio detectará y sincronizará automáticamente las configuraciones de Gradle.
- Si es necesario, sincroniza manualmente haciendo clic en Sync Project with Gradle Files.

4. **Ejecutar la aplicación**
- Selecciona el módulo Android.
- Haz clic en Run para compilar y ejecutar la app en un emulador o dispositivo físico.

## Versiones y Configuraciones Importantes ##
  
  ```bash
    Android Studio: Android Studio Ladybug Feature Drop o superior.
    Gradle: 8.5.2.
    Kotlin: 2.1.0.
  ```

## Ejecución de Tests Unitarios

Los tests unitarios se encuentran en el módulo commonTest.

Para ejecutarlos:
- Haz clic derecho sobre el directorio test y selecciona Run Tests desde Android Studio.

## Buenas Prácticas ##

  ```bash
    - Modularidad: Código organizado en módulos según su funcionalidad.
	- Inyección de Dependencias: Uso de Koin para facilitar pruebas y mantenimiento.
	- Separation of Concerns: Aplicación de Clean Architecture para separar la lógica de negocio, la presentación y la gestión de datos.
	- Pruebas Automatizadas: Implementación de tests unitarios para garantizar la calidad del código.
  ```

