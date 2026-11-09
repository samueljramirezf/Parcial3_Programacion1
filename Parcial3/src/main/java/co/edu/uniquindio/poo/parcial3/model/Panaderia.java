package co.edu.uniquindio.poo.parcial3.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

public class Panaderia {
    private final String nombre;
    private ArrayList<Empleado>listaDeEmpleados= new ArrayList<>();
    private ArrayList<Cliente>listaDeClientes= new ArrayList<>();
    private LinkedList<Pedido>listaDePedidos= new LinkedList<>();

    public Panaderia(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public LinkedList<Pedido> getListaDePedidos() {
        return listaDePedidos;
    }

    public void setListaDePedidos(LinkedList<Pedido> listaDePedidos) {
        this.listaDePedidos = listaDePedidos;
    }

    public ArrayList<Cliente> getListaDeClientes() {
        return listaDeClientes;
    }

    public void setListaDeClientes(ArrayList<Cliente> listaDeClientes) {
        this.listaDeClientes = listaDeClientes;
    }

    public ArrayList<Empleado> getListaDeEmpleados() {
        return listaDeEmpleados;
    }

    public void setListaDeEmpleados(ArrayList<Empleado> listaDeEmpleados) {
        this.listaDeEmpleados = listaDeEmpleados;
    }

    // ---------------------------------------------------------------- Clientes

    public void registrarCliente(Cliente cliente) {
        if (cliente == null || cliente.getDocumento() == null) {
            throw new IllegalArgumentException("El cliente y su documento son obligatorios");
        }
        if (buscarCliente(cliente.getDocumento()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un cliente con el documento " + cliente.getDocumento());
        }
        listaDeClientes.add(cliente);
    }

    public Optional<Cliente> buscarCliente(String documento) {
        return listaDeClientes.stream()
                .filter(cliente -> cliente.getDocumento().equals(documento))
                .findFirst();
    }

    // --------------------------------------------------------------- Empleados

    public void registrarEmpleado(Empleado empleado) {
        if (empleado == null || empleado.getCodigo() == null) {
            throw new IllegalArgumentException("El empleado y su código son obligatorios");
        }
        if (buscarEmpleado(empleado.getCodigo()).isPresent()) {
            throw new EmpleadoDuplicadoException(empleado.getCodigo());
        }
        listaDeEmpleados.add(empleado);
    }

    public Optional<Empleado> buscarEmpleado(String codigo) {
        return listaDeEmpleados.stream()
                .filter(empleado -> empleado.getCodigo().equals(codigo))
                .findFirst();
    }

    public int consultarMinutosDisponibles(String codigoEmpleado) {
        return buscarEmpleado(codigoEmpleado)
                .orElseThrow(() -> new IllegalArgumentException("No existe un empleado con el código " + codigoEmpleado))
                .getMinutosDisponibles();
    }

    // ----------------------------------------------------------------- Pedidos

    /**
     * Registra un pedido en estado PENDIENTE sin reservar minutos.
     * El código no puede repetirse en todo el historial, incluidos los pedidos cancelados.
     */
    public void registrarPedido(String codigo, Cliente cliente, TipoPan tipoPan, int cantidad) {
        if (codigo == null || cliente == null || tipoPan == null) {
            throw new IllegalArgumentException("El código, el cliente y el tipo de pan son obligatorios");
        }
        if (buscarPedido(codigo).isPresent()) {
            throw new IllegalArgumentException("Ya existe un pedido con el código " + codigo);
        }
        // El constructor valida que la cantidad sea mayor que cero.
        Pedido pedido = new Pedido(codigo, cliente, tipoPan, cantidad, this);
        listaDePedidos.add(pedido);
    }

    public Optional<Pedido> buscarPedido(String codigo) {
        return listaDePedidos.stream()
                .filter(pedido -> pedido.getCodigo().equals(codigo))
                .findFirst();
    }

    /**
     * Asigna el pedido al panadero que lo termina en menos minutos entre los que tienen minutos suficientes.
     * En empate gana el primero registrado. Calcular el tiempo no consume minutos.
     */
    public void asignarPedido(String codigoPedido) {
        Pedido pedido = obtenerPedido(codigoPedido);
        if (pedido.getEstado() != EstadoDelPedido.PENDIENTE) {
            throw new IllegalStateException("El pedido " + codigoPedido + " no está PENDIENTE");
        }

        Empleado mejorPanadero = null;
        int menorTiempo = 0;
        for (Empleado empleado : listaDeEmpleados) {
            if (empleado instanceof Panadero panadero) {
                int minutos = panadero.calcularMinutos(pedido);
                // "<" estricto: en empate se conserva el primero de la lista.
                if (empleado.tieneMinutos(minutos) && (mejorPanadero == null || minutos < menorTiempo)) {
                    mejorPanadero = empleado;
                    menorTiempo = minutos;
                }
            }
        }
        if (mejorPanadero == null) {
            throw new IllegalStateException("Ningún panadero tiene minutos suficientes para el pedido " + codigoPedido);
        }
        pedido.asignar(mejorPanadero, menorTiempo);
    }

    /**
     * Cancela el pedido. Si estaba ASIGNADO, el pedido devuelve los minutos reservados al panadero.
     * El pedido cancelado permanece en el historial.
     */
    public void cancelarPedido(String codigoPedido) {
        obtenerPedido(codigoPedido).cancelar();
    }

    /** Copia del historial completo (incluye cancelados) para que no se modifique desde fuera. */
    public ArrayList<Pedido> consultarHistorialPedidos() {
        return new ArrayList<>(listaDePedidos);
    }

    private Pedido obtenerPedido(String codigoPedido) {
        return buscarPedido(codigoPedido)
                .orElseThrow(() -> new IllegalArgumentException("No existe un pedido con el código " + codigoPedido));
    }
}
