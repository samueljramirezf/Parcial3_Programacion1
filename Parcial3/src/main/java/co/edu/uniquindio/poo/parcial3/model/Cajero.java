package co.edu.uniquindio.poo.parcial3.model;

public class Cajero extends Empleado {
    private String caja;

    public Cajero(String nombre, String documento, Panaderia ownedByPanderia, int minutosDisponibles, String codigo, String caja) {
        super(nombre, documento, ownedByPanderia, minutosDisponibles, codigo);
        this.caja = caja;
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }
}
