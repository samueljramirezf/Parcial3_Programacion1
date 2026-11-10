package co.edu.uniquindio.poo.parcial3.controller;

import java.util.Collection;

import co.edu.uniquindio.poo.parcial3.model.Cajero;
import co.edu.uniquindio.poo.parcial3.model.Empleado;
import co.edu.uniquindio.poo.parcial3.model.Panaderia;
import co.edu.uniquindio.poo.parcial3.model.PanaderoArtesanal;
import co.edu.uniquindio.poo.parcial3.model.PanaderoIndustrial;

/**
 * Intermediario entre la vista y el modelo para los empleados. No usa JavaFX.
 * Las excepciones del modelo se dejan pasar para que el viewController las muestre.
 */
public class EmpleadoController {

    public static final String TIPO_CAJERO = "Cajero";
    public static final String TIPO_ARTESANAL = "Panadero artesanal";
    public static final String TIPO_INDUSTRIAL = "Panadero industrial";

    Panaderia panaderia;

    public EmpleadoController(Panaderia panaderia) {
        this.panaderia = panaderia;
    }

    public void crearEmpleado(Empleado empleado) {
        panaderia.registrarEmpleado(empleado);
    }

    public Collection<Empleado> obtenerListaEmpleados() {
        return panaderia.getListaDeEmpleados();
    }

    /** Nombre del tipo de empleado para mostrarlo en las tablas. */
    public String obtenerTipo(Empleado empleado) {
        if (empleado instanceof Cajero) {
            return TIPO_CAJERO;
        }
        if (empleado instanceof PanaderoArtesanal) {
            return TIPO_ARTESANAL;
        }
        if (empleado instanceof PanaderoIndustrial) {
            return TIPO_INDUSTRIAL;
        }
        return "Empleado";
    }
}
