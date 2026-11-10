package co.edu.uniquindio.poo.parcial3.controller;

import java.util.Collection;
import java.util.Optional;

import co.edu.uniquindio.poo.parcial3.model.Cliente;
import co.edu.uniquindio.poo.parcial3.model.Panaderia;
import co.edu.uniquindio.poo.parcial3.model.Pedido;
import co.edu.uniquindio.poo.parcial3.model.TipoPan;

/**
 * Intermediario entre la vista y el modelo para los pedidos. No usa JavaFX.
 * Las excepciones del modelo se dejan pasar para que el viewController las muestre.
 */
public class PedidoController {

    Panaderia panaderia;

    public PedidoController(Panaderia panaderia) {
        this.panaderia = panaderia;
    }

    public void registrarPedido(String codigo, Cliente cliente, TipoPan tipoPan, int cantidad) {
        panaderia.registrarPedido(codigo, cliente, tipoPan, cantidad);
    }

    public void asignarPedido(String codigo) {
        panaderia.asignarPedido(codigo);
    }

    public void cancelarPedido(String codigo) {
        panaderia.cancelarPedido(codigo);
    }

    public Optional<Pedido> buscarPedido(String codigo) {
        return panaderia.buscarPedido(codigo);
    }

    /** Historial completo, incluidos los pedidos cancelados. */
    public Collection<Pedido> obtenerListaPedidos() {
        return panaderia.consultarHistorialPedidos();
    }
}
