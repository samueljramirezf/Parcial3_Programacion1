package co.edu.uniquindio.poo.parcial3.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PanaderiaTest {
//Test 1
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
    //Test 2
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
    //Test 3
    @Test
    public void registrarPedidoConCantidadCero() {

        Panaderia panaderia = new Panaderia("Mi Panaderia");
        Cliente cliente = new Cliente("Juan", "12345");

        assertThrows(IllegalArgumentException.class, () ->
                panaderia.registrarPedido("P02", cliente, TipoPan.TRADICIONAL, 0));

        assertTrue(panaderia.consultarHistorialPedidos().isEmpty());
    }
    //Test 4
    @Test
    public void asignarPedidoAlPanaderoMasRapido() {

        Panaderia panaderia = new Panaderia("Mi Panaderia");

        Cliente cliente = new Cliente("Juan", "12345");

        PanaderoArtesanal ana = new PanaderoArtesanal("Ana", "111", panaderia, 100, "A01");
        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "222", panaderia, 45, "I01");

        panaderia.registrarEmpleado(ana);
        panaderia.registrarEmpleado(carlos);

        panaderia.registrarPedido("P01", cliente, TipoPan.TRADICIONAL, 25);

        panaderia.asignarPedido("P01");

        Pedido pedido = panaderia.buscarPedido("P01").orElseThrow();

        assertEquals(EstadoDelPedido.ASIGNADO, pedido.getEstado());
        assertEquals(carlos, pedido.getPanaderoAsignado());
        assertEquals(45, pedido.getMinutosReservados());
        assertEquals(0, carlos.getMinutosDisponibles());
        assertEquals(100, ana.getMinutosDisponibles());
    }
    //Test 5
    @Test
    public void asignarPedidoEnCasoDeEmpate() {

        Panaderia panaderia = new Panaderia("Mi Panaderia");

        Cliente cliente = new Cliente("Juan", "12345");

        PanaderoArtesanal ana = new PanaderoArtesanal("Ana", "111", panaderia, 80, "A01");
        PanaderoArtesanal diana = new PanaderoArtesanal("Diana", "222", panaderia, 80, "A02");
        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "333", panaderia, 54, "I01");

        panaderia.registrarEmpleado(ana);
        panaderia.registrarEmpleado(diana);
        panaderia.registrarEmpleado(carlos);

        panaderia.registrarPedido("P02", cliente, TipoPan.INTEGRAL, 13);

        panaderia.asignarPedido("P02");

        Pedido pedido = panaderia.buscarPedido("P02").orElseThrow();

        assertEquals(EstadoDelPedido.ASIGNADO, pedido.getEstado());
        assertEquals(ana, pedido.getPanaderoAsignado());
        assertEquals(80, pedido.getMinutosReservados());
        assertEquals(0, ana.getMinutosDisponibles());
        assertEquals(80, diana.getMinutosDisponibles());
        assertEquals(54, carlos.getMinutosDisponibles());
    }
    //Test 6
    @Test
    public void asignarPedidoSinPanaderosDisponibles() {

        Panaderia panaderia = new Panaderia("Mi Panaderia");

        Cliente cliente = new Cliente("Juan", "12345");

        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "111", panaderia, 44, "I01");
        Cajero lucia = new Cajero("Lucia", "222", panaderia, 120, "C01", "Caja 1");

        panaderia.registrarEmpleado(carlos);
        panaderia.registrarEmpleado(lucia);

        panaderia.registrarPedido("P03", cliente, TipoPan.TRADICIONAL, 25);

        assertThrows(IllegalStateException.class, () ->
                panaderia.asignarPedido("P03"));

        Pedido pedido = panaderia.buscarPedido("P03").orElseThrow();

        assertEquals(EstadoDelPedido.PENDIENTE, pedido.getEstado());
        assertEquals(44, carlos.getMinutosDisponibles());
        assertEquals(120, lucia.getMinutosDisponibles());
    }
    //Test 7
    @Test
    public void cancelarPrimerPedidoDeDosAsignados() {

        Panaderia panaderia = new Panaderia("Mi Panaderia");

        Cliente cliente = new Cliente("Juan", "12345");

        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "111", panaderia, 120, "I01");

        panaderia.registrarEmpleado(carlos);

        panaderia.registrarPedido("P01", cliente, TipoPan.TRADICIONAL, 25);
        panaderia.registrarPedido("P02", cliente, TipoPan.TRADICIONAL, 25);

        panaderia.asignarPedido("P01");
        panaderia.asignarPedido("P02");

        panaderia.cancelarPedido("P01");

        Pedido pedido1 = panaderia.buscarPedido("P01").orElseThrow();
        Pedido pedido2 = panaderia.buscarPedido("P02").orElseThrow();

        assertEquals(EstadoDelPedido.CANCELADO, pedido1.getEstado());
        assertEquals(EstadoDelPedido.ASIGNADO, pedido2.getEstado());
        assertEquals(45, pedido1.getMinutosReservados());
        assertEquals(45, pedido2.getMinutosReservados());
        assertEquals(75, carlos.getMinutosDisponibles());
    }
    //Test 8
    @Test
    public void cancelarPedidoPendiente() {

        Panaderia panaderia = new Panaderia("Mi Panaderia");

        Cliente cliente = new Cliente("Juan", "12345");

        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "111", panaderia, 100, "I01");

        panaderia.registrarEmpleado(carlos);

        panaderia.registrarPedido("P01", cliente, TipoPan.TRADICIONAL, 25);

        panaderia.cancelarPedido("P01");

        Pedido pedido = panaderia.buscarPedido("P01").orElseThrow();

        assertEquals(EstadoDelPedido.CANCELADO, pedido.getEstado());
        assertEquals(1, panaderia.consultarHistorialPedidos().size());
        assertEquals(100, carlos.getMinutosDisponibles());
    }
    //Test 9
    @Test
    public void cancelarDosVecesElMismoPedido() {

        Panaderia panaderia = new Panaderia("Mi Panaderia");

        Cliente cliente = new Cliente("Juan", "12345");

        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "111", panaderia, 120, "I01");

        panaderia.registrarEmpleado(carlos);

        panaderia.registrarPedido("P01", cliente, TipoPan.TRADICIONAL, 25);

        panaderia.asignarPedido("P01");

        panaderia.cancelarPedido("P01");

        assertThrows(IllegalStateException.class, () ->
                panaderia.cancelarPedido("P01"));

        Pedido pedido = panaderia.buscarPedido("P01").orElseThrow();

        assertEquals(EstadoDelPedido.CANCELADO, pedido.getEstado());
        assertEquals(120, carlos.getMinutosDisponibles());
    }
}