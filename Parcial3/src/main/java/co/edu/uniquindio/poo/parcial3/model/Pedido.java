package co.edu.uniquindio.poo.parcial3.model;

public class Pedido {
    private String codigo;
    private int cantidad;
    private TipoPan tipoPan;
    private EstadoDelPedido estado;
    private int minutosReservados;
    private Cliente cliente;
    private Empleado panaderoAsignado;
    Panaderia ownedByPanaderia;

    public Pedido(String codigo, Cliente cliente, TipoPan tipoPan, int cantidad) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.tipoPan = tipoPan;
        this.cantidad = cantidad;
        this.estado = EstadoDelPedido.PENDIENTE;
        this.minutosReservados = 0;
        this.panaderoAsignado = null;
        this.ownedByPanaderia = ownedByPanaderia;
    }


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public TipoPan getTipoPan() {
        return tipoPan;
    }

    public void setTipoPan(TipoPan tipoPan) {
        this.tipoPan = tipoPan;
    }

    public EstadoDelPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoDelPedido estado) {
        this.estado = estado;
    }

    public int getMinutosReservados() {
        return minutosReservados;
    }

    public void setMinutosReservados(int minutosReservados) {
        this.minutosReservados = minutosReservados;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getPanaderoAsignado() {
        return panaderoAsignado;
    }

    public void setPanaderoAsignado(Empleado panaderoAsignado) {
        this.panaderoAsignado = panaderoAsignado;
    }
    public Panaderia getOwnedByPanaderia() {
        return ownedByPanaderia;
    }

    public void setOwnedByPanaderia(Panaderia ownedByPanaderia) {
        this.ownedByPanaderia = ownedByPanaderia;
    }
}
