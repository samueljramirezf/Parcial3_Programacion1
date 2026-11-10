package co.edu.uniquindio.poo.parcial3;

import java.io.IOException;

import co.edu.uniquindio.poo.parcial3.model.Cajero;
import co.edu.uniquindio.poo.parcial3.model.Cliente;
import co.edu.uniquindio.poo.parcial3.model.Panaderia;
import co.edu.uniquindio.poo.parcial3.model.PanaderoArtesanal;
import co.edu.uniquindio.poo.parcial3.model.PanaderoIndustrial;
import co.edu.uniquindio.poo.parcial3.viewController.ClienteViewController;
import co.edu.uniquindio.poo.parcial3.viewController.EmpleadoViewController;
import co.edu.uniquindio.poo.parcial3.viewController.PedidoViewController;
import co.edu.uniquindio.poo.parcial3.viewController.PrimaryViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private Stage primaryStage;
    public static Panaderia panaderia = new Panaderia("El Trigal");

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Panadería " + panaderia.getNombre());
        // Se llama una sola vez: si se llamara en openViewPrincipal, al volver al menú
        // el modelo lanzaría la excepción de cliente o empleado duplicado.
        inicializarData();
        openViewPrincipal();
    }

    public void openViewPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("primary.fxml"));
            Parent rootLayout = loader.load();
            PrimaryViewController primaryViewController = loader.getController();
            primaryViewController.setApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openClientes() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("crudCliente.fxml"));
            Parent rootLayout = loader.load();
            ClienteViewController clienteViewController = loader.getController();
            clienteViewController.setApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openEmpleados() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("crudEmpleado.fxml"));
            Parent rootLayout = loader.load();
            EmpleadoViewController empleadoViewController = loader.getController();
            empleadoViewController.setApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openPedidos() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("crudPedido.fxml"));
            Parent rootLayout = loader.load();
            PedidoViewController pedidoViewController = loader.getController();
            pedidoViewController.setApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

    //servicios

    /**
     * Datos de demostración. Los panaderos tienen pocos minutos a propósito para que
     * no puedan tomar el pedido P01 del guion (25 panes tradicionales necesitan 90 min
     * con un artesanal y 45 con un industrial) y el pedido le quede a Carlos.
     */
    public void inicializarData() {
        panaderia.registrarCliente(new Cliente("María", "1001"));
        panaderia.registrarEmpleado(new PanaderoArtesanal("Luis", "2001", panaderia, 30, "A01"));
        panaderia.registrarEmpleado(new PanaderoIndustrial("Sofía", "2002", panaderia, 20, "I01"));
        panaderia.registrarEmpleado(new Cajero("Pedro", "3001", panaderia, 480, "C01", "Caja 1"));
    }
}
