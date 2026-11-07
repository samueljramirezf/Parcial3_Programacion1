package co.edu.uniquindio.poo.parcial3.model;

public class PanaderoIndustrial extends Empleado implements Panadero {

    public PanaderoIndustrial(String nombre, String documento, Panaderia ownedByPanderia, int minutosDisponibles, String codigo) {
        super(nombre, documento, ownedByPanderia, minutosDisponibles, codigo);
    }

    @Override
    public int calcularMinutos(Pedido pedido) {
        //Todavia no he implementado excepciones
        throw new UnsupportedOperationException("Pendiente de implementar");
    }
}
