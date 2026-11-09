package co.edu.uniquindio.poo.parcial3.model;

public abstract class Empleado extends Persona{
    private String codigo;
    private int minutosDisponibles;
    private Panaderia ownedByPanaderia;

    public Empleado(String nombre, String documento, Panaderia ownedByPanaderia, int minutosDisponibles, String codigo) {
        super(nombre, documento);
        this.ownedByPanaderia = ownedByPanaderia;
        this.minutosDisponibles = minutosDisponibles;
        this.codigo = codigo;
    }
    public boolean tieneMinutos(int minutos) {
        return minutos > 0 && minutosDisponibles >= minutos;
    }
    public void reservarMinutos(int minutos) {
        if (minutos <= 0) {
            throw new IllegalArgumentException("Los minutos a reservar deben ser mayores que cero");
        }
        if (!tieneMinutos(minutos)) {
            throw new IllegalStateException("El empleado " + codigo + " no tiene minutos suficientes");
        }
        minutosDisponibles -= minutos;
    }
    public void devolverMinutos(int minutos) {
        if (minutos <= 0) {
            throw new IllegalArgumentException("Los minutos a devolver deben ser mayores que cero");
        }
        minutosDisponibles += minutos;
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
