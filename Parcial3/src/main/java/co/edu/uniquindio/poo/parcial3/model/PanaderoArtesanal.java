package co.edu.uniquindio.poo.parcial3.model;

public class PanaderoArtesanal extends Empleado implements Panadero {

    public PanaderoArtesanal(String nombre, String documento, Panaderia ownedByPanderia, int minutosDisponibles, String codigo) {
        super(nombre, documento, ownedByPanderia, minutosDisponibles, codigo);
    }

    @Override
    public int calcularMinutos(Pedido pedido) {
        //todavia no he implementado excepciones
        throw new UnsupportedOperationException("Pendiente de implementar");
    }
}
