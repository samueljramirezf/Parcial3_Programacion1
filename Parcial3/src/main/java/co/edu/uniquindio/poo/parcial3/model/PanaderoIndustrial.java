package co.edu.uniquindio.poo.parcial3.model;

public class PanaderoIndustrial extends Empleado implements Panadero {
    private static final int PANES_POR_LOTE = 30;
    private static final int MINUTOS_PREPARACION = 20;
    private static final int MINUTOS_LOTE_TRADICIONAL = 25;
    private static final int MINUTOS_LOTE_INTEGRAL = 35;

    public PanaderoIndustrial(String nombre, String documento, Panaderia ownedByPanaderia, int minutosDisponibles, String codigo) {
        super(nombre, documento, ownedByPanaderia, minutosDisponibles, codigo);
    }

    @Override
    public int calcularMinutos(Pedido pedido) {
        int minutosPorLote = switch (pedido.getTipoPan()) {
            case TRADICIONAL -> MINUTOS_LOTE_TRADICIONAL;
            case INTEGRAL -> MINUTOS_LOTE_INTEGRAL;
        };
        int lotes = Math.ceilDiv(pedido.getCantidad(), PANES_POR_LOTE);
        return MINUTOS_PREPARACION + lotes * minutosPorLote;
    }
}
