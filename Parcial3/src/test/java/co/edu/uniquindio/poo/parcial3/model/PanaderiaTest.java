package co.edu.uniquindio.poo.parcial3.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PanaderiaTest {

    @Test
    public void registrarPedidoP01() {

        Panaderia panaderia = new Panaderia("Mi Panaderia");

        Cliente cliente = new Cliente("Juan", "12345");

        Cajero cajero1 = new Cajero("Carlos", "111", panaderia, 300, "C01", "Caja 1");
        Cajero cajero2 = new Cajero("Pedro", "222", panaderia, 240, "C02", "Caja 2");

        panaderia.registrarEmpleado(cajero1);
        panaderia.registrarEmpleado(cajero2);

        int minutosIniciales1 = cajero1.getMinutosDisponibles();
        int minutosIniciales2 = cajero2.getMinutosDisponibles();

        panaderia.registrarPedido("P01", cliente, TipoPan.TRADICIONAL, 25);

        Pedido pedido = panaderia.buscarPedido("P01").orElseThrow();

        assertEquals(EstadoDelPedido.PENDIENTE, pedido.getEstado());
        assertEquals(25, pedido.getCantidad());
        assertEquals(minutosIniciales1, cajero1.getMinutosDisponibles());
        assertEquals(minutosIniciales2, cajero2.getMinutosDisponibles());
    }
    @Test
    public void registrarPedidoConCodigoCancelado() {

        Panaderia panaderia = new Panaderia("Mi Panaderia");
        Cliente cliente = new Cliente("Juan", "12345");

        panaderia.registrarPedido("P01", cliente, TipoPan.TRADICIONAL, 25);

        panaderia.cancelarPedido("P01");

        assertThrows(IllegalArgumentException.class, () ->
                panaderia.registrarPedido("P01", cliente, TipoPan.TRADICIONAL, 15));

        assertEquals(1, panaderia.consultarHistorialPedidos().size());
        assertEquals(EstadoDelPedido.CANCELADO,
                panaderia.buscarPedido("P01").orElseThrow().getEstado());
    }
    @Test
    public void registrarPedidoConCantidadCero() {

        Panaderia panaderia = new Panaderia("Mi Panaderia");
        Cliente cliente = new Cliente("Juan", "12345");

        assertThrows(IllegalArgumentException.class, () ->
                panaderia.registrarPedido("P02", cliente, TipoPan.TRADICIONAL, 0));

        assertTrue(panaderia.consultarHistorialPedidos().isEmpty());
    }

}