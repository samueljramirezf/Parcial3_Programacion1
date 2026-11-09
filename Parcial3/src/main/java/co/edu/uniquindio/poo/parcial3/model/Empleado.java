package co.edu.uniquindio.poo.parcial3.model;

public class Empleado extends Persona{
    private String codigo;
    private int minutosDisponibles;
    Panaderia ownedByPanaderia;

    public Empleado(String nombre, String documento, Panaderia ownedByPanderia, int minutosDisponibles, String codigo) {
        super(nombre, documento);
        this.ownedByPanaderia = ownedByPanaderia;
        this.minutosDisponibles = minutosDisponibles;
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getMinutosDisponibles() {
        return minutosDisponibles;
    }

    public void setMinutosDisponibles(int minutosDisponibles) {
        this.minutosDisponibles = minutosDisponibles;
    }

    public Panaderia getOwnedByPanaderia() {
        return ownedByPanaderia;
    }

    public void setOwnedByPanaderia(Panaderia ownedByPanaderia) {
        this.ownedByPanaderia = ownedByPanaderia;
    }
}
