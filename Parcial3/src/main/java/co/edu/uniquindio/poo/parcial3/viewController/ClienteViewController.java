package co.edu.uniquindio.poo.parcial3.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.poo.parcial3.App;
import co.edu.uniquindio.poo.parcial3.controller.ClienteController;
import co.edu.uniquindio.poo.parcial3.model.Cliente;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ClienteViewController {

    ClienteController clienteController;
    ObservableList<Cliente> listClientes = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtDocumento;

    @FXML
    private TextField txtNombre;

    @FXML
    private Button btnAgregarCliente;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<Cliente> tblListCliente;

    @FXML
    private TableColumn<Cliente, String> tbcDocumento;

    @FXML
    private TableColumn<Cliente, String> tbcNombre;

    private App app;

    @FXML
    void onAgregarCliente() {
        agregarCliente();
    }

    @FXML
    void onLimpiar() {
        limpiarCamposCliente();
    }

    @FXML
    void onVolver() {
        app.openViewPrincipal();
    }

    @FXML
    void initialize() {
        // panaderia es static, por eso se accede con App.panaderia (app aún es null aquí)
        clienteController = new ClienteController(App.panaderia);
        initView();
    }

    private void initView() {
        // Traer los datos del cliente a la tabla
        initDataBinding();

        // Obtiene la lista
        obtenerClientes();

        // Agregar los elementos a la tabla
        tblListCliente.setItems(listClientes);
    }

    private void initDataBinding() {
        tbcDocumento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocumento()));
        tbcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
    }

    private void obtenerClientes() {
        listClientes.setAll(clienteController.obtenerListaClientes());
    }

    private void agregarCliente() {
        String documento = txtDocumento.getText().trim();
        String nombre = txtNombre.getText().trim();
        if (documento.isEmpty() || nombre.isEmpty()) {
            mostrarMensaje("El documento y el nombre son obligatorios");
            return;
        }
        Cliente cliente = new Cliente(nombre, documento);
        try {
            clienteController.crearCliente(cliente);
            listClientes.add(cliente);
            limpiarCamposCliente();
        } catch (IllegalArgumentException | IllegalStateException e) {
            mostrarMensaje(e.getMessage());
        }
    }

    private void limpiarCamposCliente() {
        txtDocumento.clear();
        txtNombre.clear();
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Gestión Clientes");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
