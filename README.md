# JavaFX_Hilos_Practica - NicaData Solutions S.A.

Práctica guiada sobre programación concurrente en JavaFX implementando hilos independientes para la simulación de procesos empresariales.

## Estructura de Hilos Implementados
1. **Hilo de Clientes (`Hilo-Clientes`)**:
    - Tarea: Procesa 100 registros con un tiempo de espera de 80ms por iteración.
    - Control: Permite iniciar, detener y reiniciar sin interferir con el hilo de productos.
2. **Hilo de Productos (`Hilo-Productos`)**:
    - Tarea: Procesa 50 registros con un tiempo de espera de 150ms por iteración.
    - Control: Ejecución concurrente e independiente.

## Características Clave
- **Actualización Segura de Interfaz**: Uso de `Platform.runLater()` para derivar las actualizaciones visuales al *JavaFX Application Thread*.
- **Detención Controlada**: Manejo de interrupciones mediante `Thread.interrupt()` y captura de `InterruptedException`.
- **Hilos Daemon**: Configuración de `setDaemon(true)` para asegurar que los hilos no bloqueen el cierre de la aplicación.

## Pregunta de Análisis (Paso 6)
**¿Por qué no debemos ejecutar procesos prolongados directamente dentro de los eventos de JavaFX?**
JavaFX utiliza un único hilo principal (*JavaFX Application Thread*) para manejar la interfaz y los eventos. Si se ejecuta un proceso pesado en este hilo (usando `Thread.sleep` o consultas pesadas), la interfaz gráfica se "congela" y la ventana deja de responder. Por ello, los procesos largos deben ejecutarse en hilos secundarios.