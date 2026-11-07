package co.edu.uniquindio.poo.parcial3.model;

import java.util.ArrayList;
import java.util.LinkedList;

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
}
