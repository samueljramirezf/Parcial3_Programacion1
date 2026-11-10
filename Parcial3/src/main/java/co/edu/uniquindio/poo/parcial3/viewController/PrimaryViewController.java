package co.edu.uniquindio.poo.parcial3.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.poo.parcial3.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PrimaryViewController {

    App app;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnEmpleados;

    @FXML
    private Button btnPedidos;

    @FXML
    void onAbrirClientes() {
        app.openClientes();
    }

    @FXML
    void onAbrirEmpleados() {
        app.openEmpleados();
    }

    @FXML
    void onAbrirPedidos() {
        app.openPedidos();
    }

    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    void initialize() {
        // No se usa "app" aquí: setApp se llama después de initialize().
    }
}
