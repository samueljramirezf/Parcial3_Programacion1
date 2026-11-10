package co.edu.uniquindio.poo.parcial3.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.poo.parcial3.App;
import co.edu.uniquindio.poo.parcial3.controller.EmpleadoController;
import co.edu.uniquindio.poo.parcial3.model.Cajero;
import co.edu.uniquindio.poo.parcial3.model.Empleado;
import co.edu.uniquindio.poo.parcial3.model.PanaderoArtesanal;
import co.edu.uniquindio.poo.parcial3.model.PanaderoIndustrial;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class EmpleadoViewController {

    EmpleadoController empleadoController;
    ObservableList<Empleado> listEmpleados = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> cmbTipo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtDocumento;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtMinutos;

    @FXML
    private TextField txtCaja;

    @FXML
    private Button btnAgregarEmpleado;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<Empleado> tblListEmpleado;

    @FXML
    private TableColumn<Empleado, String> tbcTipo;

    @FXML
    private TableColumn<Empleado, String> tbcCodigo;

    @FXML
    private TableColumn<Empleado, String> tbcNombre;

    @FXML
    private TableColumn<Empleado, String> tbcMinutos;

    private App app;

    @FXML
    void onAgregarEmpleado() {
        agregarEmpleado();
    }

    @FXML
    void onLimpiar() {
        limpiarCamposEmpleado();
    }

    @FXML
    void onVolver() {
        app.openViewPrincipal();
    }

    @FXML
    void initialize() {
        // panaderia es static, por eso se accede con App.panaderia (app aún es null aquí)
        empleadoController = new EmpleadoController(App.panaderia);
        initView();
    }

    private void initView() {
        initTipos();
        initDataBinding();
        obtenerEmpleados();
        tblListEmpleado.setItems(listEmpleados);
    }

    private void initTipos() {
        cmbTipo.setItems(FXCollections.observableArrayList(
                EmpleadoController.TIPO_CAJERO,
                EmpleadoController.TIPO_ARTESANAL,
                EmpleadoController.TIPO_INDUSTRIAL));
        // El campo de caja solo se habilita para el cajero
        txtCaja.setDisable(true);
        cmbTipo.valueProperty().addListener((obs, oldTipo, newTipo) -> {
            boolean esCajero = EmpleadoController.TIPO_CAJERO.equals(newTipo);
            txtCaja.setDisable(!esCajero);
            if (!esCajero) {
                txtCaja.clear();
            }
        });
    }

    private void initDataBinding() {
        tbcTipo.setCellValueFactory(cellData -> new SimpleStringProperty(empleadoController.obtenerTipo(cellData.getValue())));
        tbcCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        tbcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        // Los minutos son int, se convierten a String para mostrarlos
        tbcMinutos.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMinutosDisponibles())));
    }

    private void obtenerEmpleados() {
        listEmpleados.setAll(empleadoController.obtenerListaEmpleados());
    }

    private void agregarEmpleado() {
        Empleado empleado = buildEmpleado();
        if (empleado == null) {
            return;
        }
        try {
            empleadoController.crearEmpleado(empleado);
            listEmpleados.add(empleado);
            limpiarCamposEmpleado();
        } catch (IllegalArgumentException | IllegalStateException e) {
            mostrarMensaje(e.getMessage());
        }
    }

    /** Valida los campos con mensajes propios y crea el empleado del tipo elegido. Devuelve null si algo falla. */
    private Empleado buildEmpleado() {
        String tipo = cmbTipo.getValue();
        String nombre = txtNombre.getText().trim();
        String documento = txtDocumento.getText().trim();
        String codigo = txtCodigo.getText().trim();
        String textoMinutos = txtMinutos.getText().trim();
        String caja = txtCaja.getText().trim();

        if (tipo == null) {
            mostrarMensaje("Seleccione el tipo de empleado");
            return null;
        }
        if (nombre.isEmpty() || documento.isEmpty() || codigo.isEmpty() || textoMinutos.isEmpty()) {
            mostrarMensaje("El nombre, el documento, el código y los minutos son obligatorios");
            return null;
        }
        int minutos;
        try {
            minutos = Integer.parseInt(textoMinutos);
        } catch (NumberFormatException e) {
            mostrarMensaje("Los minutos deben ser un número entero");
            return null;
        }
        if (minutos < 0) {
            mostrarMensaje("Los minutos no pueden ser negativos");
            return null;
        }

        switch (tipo) {
            case EmpleadoController.TIPO_CAJERO:
                if (caja.isEmpty()) {
                    mostrarMensaje("La caja es obligatoria para el cajero");
                    return null;
                }
                return new Cajero(nombre, documento, App.panaderia, minutos, codigo, caja);
            case EmpleadoController.TIPO_ARTESANAL:
                return new PanaderoArtesanal(nombre, documento, App.panaderia, minutos, codigo);
            default:
                return new PanaderoIndustrial(nombre, documento, App.panaderia, minutos, codigo);
        }
    }

    private void limpiarCamposEmpleado() {
        cmbTipo.getSelectionModel().clearSelection();
        txtNombre.clear();
        txtDocumento.clear();
        txtCodigo.clear();
        txtMinutos.clear();
        txtCaja.clear();
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Gestión Empleados");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
