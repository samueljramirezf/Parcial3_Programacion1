package co.edu.uniquindio.poo.parcial3.model;

public class Pedido {
    private String codigo;
    private int cantidad;
    private TipoPan tipoPan;
    private EstadoDelPedido estado;
    private int minutosReservados;
    private Cliente cliente;
    private Empleado panaderoAsignado;
    private Panaderia ownedByPanaderia;

    public Pedido(String codigo, Cliente cliente, TipoPan tipoPan, int cantidad, Panaderia ownedByPanaderia) {
        validarCantidad(cantidad);
        this.codigo = codigo;
        this.cliente = cliente;
        this.tipoPan = tipoPan;
        this.cantidad = cantidad;
        this.estado = EstadoDelPedido.PENDIENTE;
        this.minutosReservados = 0;
        this.panaderoAsignado = null;
        this.ownedByPanaderia = ownedByPanaderia;
    }

    /**
     * Asigna el pedido a un panadero y le reserva los minutos.
     * Valida todo antes de modificar: si algo falla, ni el pedido ni el empleado cambian.
     */
    public void asignar(Empleado panadero, int minutos) {
        if (estado != EstadoDelPedido.PENDIENTE) {
            throw new IllegalStateException("Solo se puede asignar un pedido PENDIENTE; el pedido " + codigo + " está " + estado);
        }
        if (!(panadero instanceof Panadero)) {
            throw new IllegalArgumentException("El pedido solo se puede asignar a un panadero");
        }
        // Lanza IllegalArgumentException o IllegalStateException sin modificar al empleado.
        panadero.reservarMinutos(minutos);
        this.panaderoAsignado = panadero;
        this.minutosReservados = minutos;
        this.estado = EstadoDelPedido.ASIGNADO;
    }

    /**
     * Cancela el pedido. Si estaba ASIGNADO, devuelve al panadero exactamente los minutos reservados.
     * El panadero y los minutos reservados se conservan como registro en el historial.
     */
    public void cancelar() {
        if (estado == EstadoDelPedido.CANCELADO) {
            throw new IllegalStateException("El pedido " + codigo + " ya está CANCELADO");
        }
        if (estado == EstadoDelPedido.ASIGNADO) {
            panaderoAsignado.devolverMinutos(minutosReservados);
        }
        this.estado = EstadoDelPedido.CANCELADO;
    }

    private static void validarCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
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
        validarCantidad(cantidad);
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
