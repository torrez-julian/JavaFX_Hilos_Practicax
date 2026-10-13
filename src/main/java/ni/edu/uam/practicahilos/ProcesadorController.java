package ni.edu.uam.practicahilos;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class ProcesadorController {

    // Componentes de Clientes
    @FXML private Label lblEstadoClientes;
    @FXML private Label lblPorcentajeClientes;
    @FXML private ProgressBar barraClientes;
    @FXML private Button btnIniciarClientes;
    @FXML private Button btnDetenerClientes;
    @FXML private Button btnReiniciarClientes;

    // Componentes de Productos
    @FXML private Label lblEstadoProductos;
    @FXML private Label lblPorcentajeProductos;
    @FXML private ProgressBar barraProductos;
    @FXML private Button btnIniciarProductos;
    @FXML private Button btnDetenerProductos;
    @FXML private Button btnReiniciarProductos;

    // Hilos de ejecución independientes
    private Thread hiloClientes;
    private Thread hiloProductos;

    // --- PROCESO DE CLIENTES (100 registros, 80 ms por iteración) ---
    @FXML
    private void iniciarClientes() {
        if (hiloClientes != null && hiloClientes.isAlive()) return;

        barraClientes.setProgress(0);
        lblPorcentajeClientes.setText("0%");
        lblEstadoClientes.setText("Procesando clientes...");

        btnIniciarClientes.setDisable(true);
        btnDetenerClientes.setDisable(false);
        btnReiniciarClientes.setDisable(true);

        Runnable tarea = () -> {
            try {
                for (int i = 1; i <= 100; i++) {
                    if (Thread.currentThread().isInterrupted()) throw new InterruptedException();

                    Thread.sleep(80);

                    final int avance = i;
                    Platform.runLater(() -> {
                        barraClientes.setProgress(avance / 100.0);
                        lblPorcentajeClientes.setText(avance + "%");
                    });
                }

                Platform.runLater(() -> {
                    lblEstadoClientes.setText("Proceso de clientes finalizado");
                    habilitarBotonesClientes();
                });

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Platform.runLater(() -> {
                    lblEstadoClientes.setText("Proceso de clientes detenido");
                    habilitarBotonesClientes();
                });
            }
        };

        hiloClientes = new Thread(tarea, "Hilo-Clientes");
        hiloClientes.setDaemon(true);
        hiloClientes.start();
    }

    @FXML
    private void detenerClientes() {
        if (hiloClientes != null && hiloClientes.isAlive()) {
            btnDetenerClientes.setDisable(true);
            lblEstadoClientes.setText("Solicitando detención...");
            hiloClientes.interrupt();
        }
    }

    @FXML
    private void reiniciarClientes() {
        if (hiloClientes != null && hiloClientes.isAlive()) return;

        barraClientes.setProgress(0);
        lblPorcentajeClientes.setText("0%");
        lblEstadoClientes.setText("Esperando inicio");
        habilitarBotonesClientes();
    }

    private void habilitarBotonesClientes() {
        btnIniciarClientes.setDisable(false);
        btnDetenerClientes.setDisable(true);
        btnReiniciarClientes.setDisable(false);
    }

    // --- PROCESO DE PRODUCTOS (50 registros, 150 ms por iteración) ---
    @FXML
    private void iniciarProductos() {
        if (hiloProductos != null && hiloProductos.isAlive()) return;

        barraProductos.setProgress(0);
        lblPorcentajeProductos.setText("0%");
        lblEstadoProductos.setText("Procesando productos...");

        btnIniciarProductos.setDisable(true);
        btnDetenerProductos.setDisable(false);
        btnReiniciarProductos.setDisable(true);

        Runnable tarea = () -> {
            try {
                for (int i = 1; i <= 50; i++) {
                    if (Thread.currentThread().isInterrupted()) throw new InterruptedException();

                    Thread.sleep(150);

                    final int avance = i;
                    Platform.runLater(() -> {
                        barraProductos.setProgress(avance / 50.0);
                        int porcentaje = (int) ((avance / 50.0) * 100);
                        lblPorcentajeProductos.setText(porcentaje + "%");
                    });
                }

                Platform.runLater(() -> {
                    lblEstadoProductos.setText("Proceso de productos finalizado");
                    habilitarBotonesProductos();
                });

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Platform.runLater(() -> {
                    lblEstadoProductos.setText("Proceso de productos detenido");
                    habilitarBotonesProductos();
                });
            }
        };

        hiloProductos = new Thread(tarea, "Hilo-Productos");
        hiloProductos.setDaemon(true);
        hiloProductos.start();
    }

    @FXML
    private void detenerProductos() {
        if (hiloProductos != null && hiloProductos.isAlive()) {
            btnDetenerProductos.setDisable(true);
            lblEstadoProductos.setText("Solicitando detención...");
            hiloProductos.interrupt();
        }
    }

    @FXML
    private void reiniciarProductos() {
        if (hiloProductos != null && hiloProductos.isAlive()) return;

        barraProductos.setProgress(0);
        lblPorcentajeProductos.setText("0%");
        lblEstadoProductos.setText("Esperando inicio");
        habilitarBotonesProductos();
    }

    private void habilitarBotonesProductos() {
        btnIniciarProductos.setDisable(false);
        btnDetenerProductos.setDisable(true);
        btnReiniciarProductos.setDisable(false);
    }

    // --- DETENCIÓN GENERAL AL CERRAR VENTANA ---
    public void detenerAlCerrar() {
        if (hiloClientes != null && hiloClientes.isAlive()) {
            hiloClientes.interrupt();
        }
        if (hiloProductos != null && hiloProductos.isAlive()) {
            hiloProductos.interrupt();
        }
    }
}