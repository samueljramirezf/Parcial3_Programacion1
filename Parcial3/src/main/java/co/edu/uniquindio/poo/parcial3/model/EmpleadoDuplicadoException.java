package co.edu.uniquindio.poo.parcial3.model;

/**
 * Se lanza al registrar un empleado cuyo código ya existe en la panadería.
 */
public class EmpleadoDuplicadoException extends IllegalArgumentException {
    public EmpleadoDuplicadoException(String codigo) {
        super("Ya existe un empleado con el código " + codigo);
    }
}
