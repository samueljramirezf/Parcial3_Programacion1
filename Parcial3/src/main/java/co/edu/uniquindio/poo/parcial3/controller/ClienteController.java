package co.edu.uniquindio.poo.parcial3.controller;

import java.util.Collection;

import co.edu.uniquindio.poo.parcial3.model.Cliente;
import co.edu.uniquindio.poo.parcial3.model.Panaderia;

/**
 * Intermediario entre la vista y el modelo para los clientes. No usa JavaFX.
 * Las excepciones del modelo se dejan pasar para que el viewController las muestre.
 */
public class ClienteController {

    Panaderia panaderia;

    public ClienteController(Panaderia panaderia) {
        this.panaderia = panaderia;
    }

    public void crearCliente(Cliente cliente) {
        panaderia.registrarCliente(cliente);
    }

    public Collection<Cliente> obtenerListaClientes() {
        return panaderia.getListaDeClientes();
    }
}
