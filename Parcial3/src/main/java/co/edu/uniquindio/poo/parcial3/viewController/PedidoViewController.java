package co.edu.uniquindio.poo.parcial3.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.poo.parcial3.App;
import co.edu.uniquindio.poo.parcial3.controller.ClienteController;
import co.edu.uniquindio.poo.parcial3.controller.EmpleadoController;
import co.edu.uniquindio.poo.parcial3.controller.PedidoController;
import co.edu.uniquindio.poo.parcial3.model.Cliente;
import co.edu.uniquindio.poo.parcial3.model.Empleado;
import co.edu.uniquindio.poo.parcial3.model.EstadoDelPedido;
import co.edu.uniquindio.poo.parcial3.model.Pedido;
import co.edu.uniquindio.poo.parcial3.model.TipoPan;
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
import javafx.util.StringConverter;

public class PedidoViewController {

    PedidoController pedidoController;
    ClienteController clienteController;
    EmpleadoController empleadoController;
    ObservableList<Pedido> listPedidos = FXCollections.observableArrayList();
    ObservableList<Empleado> listEmpleados = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtCodigo;

    @FXML
    private ComboBox<Cliente> cmbCliente;

    @FXML
    private ComboBox<TipoPan> cmbTipoPan;

    @FXML
    private TextField txtCantidad;

    @FXML
    private Button btnRegistrar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnAsignar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<Pedido> tblListPedido;

    @FXML
    private TableColumn<Pedido, String> tbcCodigo;

    @FXML
    private TableColumn<Pedido, String> tbcCliente;

    @FXML
    private TableColumn<Pedido, String> tbcTipoPan;

    @FXML
    private TableColumn<Pedido, String> tbcCantidad;

    @FXML
    private TableColumn<Pedido, String> tbcEstado;

    @FXML
    private TableColumn<Pedido, String> tbcPanadero;

    @FXML
    private TableColumn<Pedido, String> tbcMinutosReservados;

    @FXML
    private TableView<Empleado> tblListEmpleado;

    @FXML
    private TableColumn<Empleado, String> tbcEmpTipo;

    @FXML
    private TableColumn<Empleado, String> tbcEmpCodigo;

    @FXML
    private TableColumn<Empleado, String> tbcEmpNombre;

    @FXML
    private TableColumn<Empleado, String> tbcEmpMinutos;

    private App app;

    @FXML
    void onRegistrarPedido() {
        registrarPedido();
    }

    @FXML
    void onLimpiar() {
        limpiarCamposPedido();
    }

    @FXML
    void onAsignarPedido() {
        asignarPedido();
    }

    @FXML
    void onCancelarPedido() {
        cancelarPedido();
    }

    @FXML
    void onVolver() {
        app.openViewPrincipal();
    }

    @FXML
    void initialize() {
        // panaderia es static, por eso se accede con App.panaderia (app aún es null aquí)
        pedidoController = new PedidoController(App.panaderia);
        clienteController = new ClienteController(App.panaderia);
        empleadoController = new EmpleadoController(App.panaderia);
        initView();
    }

    private void initView() {
        initCombos();
        initDataBinding();
        recargarListas();
        tblListPedido.setItems(listPedidos);
        tblListEmpleado.setItems(listEmpleados);
    }

    private void initCombos() {
        cmbCliente.setItems(FXCollections.observableArrayList(clienteController.obtenerListaClientes()));
        // Muestra el cliente como "nombre (documento)" en lugar del toString por defecto
        cmbCliente.setConverter(new StringConverter<>() {
            @Override
            public String toString(Cliente cliente) {
                return cliente == null ? "" : cliente.getNombre() + " (" + cliente.getDocumento() + ")";
            }

            @Override
            public Cliente fromString(String texto) {
                return null;
            }
        });
        cmbTipoPan.setItems(FXCollections.observableArrayList(TipoPan.values()));
    }

    private void initDataBinding() {
        tbcCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        tbcCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getNombre()));
        tbcTipoPan.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoPan().toString()));
        tbcCantidad.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCantidad())));
        tbcEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstado().toString()));
        tbcPanadero.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getPanaderoAsignado() == null ? "-" : cellData.getValue().getPanaderoAsignado().getNombre()));
        tbcMinutosReservados.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMinutosReservados())));

        tbcEmpTipo.setCellValueFactory(cellData -> new SimpleStringProperty(empleadoController.obtenerTipo(cellData.getValue())));
        tbcEmpCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        tbcEmpNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tbcEmpMinutos.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMinutosDisponibles())));
    }

    /** Vuelve a pedir las listas al modelo y refresca las tablas para ver estados y minutos actualizados. */
    private void recargarListas() {
        listPedidos.setAll(pedidoController.obtenerListaPedidos());
        listEmpleados.setAll(empleadoController.obtenerListaEmpleados());
        tblListPedido.refresh();
        tblListEmpleado.refresh();
    }

    private void registrarPedido() {
        String codigo = txtCodigo.getText().trim();
        Cliente cliente = cmbCliente.getValue();
        TipoPan tipoPan = cmbTipoPan.getValue();
        String textoCantidad = txtCantidad.getText().trim();

        if (codigo.isEmpty()) {
            mostrarMensaje("El código del pedido es obligatorio", Alert.AlertType.WARNING);
            return;
        }
        if (cliente == null || tipoPan == null) {
            mostrarMensaje("Seleccione el cliente y el tipo de pan", Alert.AlertType.WARNING);
            return;
        }
        int cantidad;
        try {
            cantidad = Integer.parseInt(textoCantidad);
        } catch (NumberFormatException e) {
            mostrarMensaje("La cantidad debe ser un número entero", Alert.AlertType.WARNING);
            return;
        }
        // Que la cantidad sea mayor que cero lo valida el modelo (Pedido)
        try {
            pedidoController.registrarPedido(codigo, cliente, tipoPan, cantidad);
            recargarListas();
            limpiarCamposPedido();
        } catch (IllegalArgumentException | IllegalStateException e) {
            mostrarMensaje(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void asignarPedido() {
        Pedido seleccionado = tblListPedido.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarMensaje("Seleccione un pedido de la tabla", Alert.AlertType.WARNING);
            return;
        }
        try {
            pedidoController.asignarPedido(seleccionado.getCodigo());
            recargarListas();
            mostrarMensaje("El pedido " + seleccionado.getCodigo() + " quedó ASIGNADO a "
                    + seleccionado.getPanaderoAsignado().getNombre() + " con "
                    + seleccionado.getMinutosReservados() + " minutos reservados", Alert.AlertType.INFORMATION);
        } catch (IllegalArgumentException | IllegalStateException e) {
            mostrarMensaje(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void cancelarPedido() {
        Pedido seleccionado = tblListPedido.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarMensaje("Seleccione un pedido de la tabla", Alert.AlertType.WARNING);
            return;
        }
        boolean estabaAsignado = seleccionado.getEstado() == EstadoDelPedido.ASIGNADO;
        try {
            pedidoController.cancelarPedido(seleccionado.getCodigo());
            recargarListas();
            String mensaje = "El pedido " + seleccionado.getCodigo() + " quedó CANCELADO";
            if (estabaAsignado) {
                mensaje += ". Se devolvieron " + seleccionado.getMinutosReservados() + " minutos a "
                        + seleccionado.getPanaderoAsignado().getNombre();
            }
            mostrarMensaje(mensaje, Alert.AlertType.INFORMATION);
        } catch (IllegalArgumentException | IllegalStateException e) {
            mostrarMensaje(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void limpiarCamposPedido() {
        txtCodigo.clear();
        cmbCliente.getSelectionModel().clearSelection();
        cmbTipoPan.getSelectionModel().clearSelection();
        txtCantidad.clear();
    }

    private void mostrarMensaje(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Gestión Pedidos");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
