package co.edu.uniquindio.poo.parcial3.model;

public class PanaderoArtesanal extends Empleado implements Panadero {
    private static final int PANES_POR_BANDEJA = 12;
    private static final int MINUTOS_BANDEJA_TRADICIONAL = 30;
    private static final int MINUTOS_BANDEJA_INTEGRAL = 40;

    public PanaderoArtesanal(String nombre, String documento, Panaderia ownedByPanaderia, int minutosDisponibles, String codigo) {
        super(nombre, documento, ownedByPanaderia, minutosDisponibles, codigo);
    }

    @Override
    public int calcularMinutos(Pedido pedido) {
        int minutosPorBandeja = switch (pedido.getTipoPan()) {
            case TRADICIONAL -> MINUTOS_BANDEJA_TRADICIONAL;
            case INTEGRAL -> MINUTOS_BANDEJA_INTEGRAL;
        };
        int bandejas = Math.ceilDiv(pedido.getCantidad(), PANES_POR_BANDEJA);
        return bandejas * minutosPorBandeja;
    }
}
