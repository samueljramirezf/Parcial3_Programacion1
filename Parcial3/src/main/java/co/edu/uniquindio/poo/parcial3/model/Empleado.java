package co.edu.uniquindio.poo.parcial3.model;

public class Empleado extends Persona{
    private String codigo;
    private int minutosDisponibles;
    Panaderia ownedByPanderia;

    public Empleado(String nombre, String documento, Panaderia ownedByPanderia, int minutosDisponibles, String codigo) {
        super(nombre, documento);
        this.ownedByPanderia = ownedByPanderia;
        this.minutosDisponibles = minutosDisponibles;
        this.codigo = codigo;
    }
    public boolean tieneMinutos(int minutos);

    public void reservarMinutos(int minutos);

    public void devolverMinutos(int minutos);

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

    public Panaderia getOwnedByPanderia() {
        return ownedByPanderia;
    }

    public void setOwnedByPanderia(Panaderia ownedByPanderia) {
        this.ownedByPanderia = ownedByPanderia;
    }

    public boolean tieneMinutos(int minutos) {
        return minutos > 0 && minutosDisponibles >= minutos;
    }
    public void reservarMinutos(int minutos) {
        if (tieneMinutos(minutos)) {
            minutosDisponibles -= minutos;
        }
    }
    public void devolverMinutos(int minutos) {
        if (minutos > 0) {
            minutosDisponibles += minutos;
        }
    }
}
