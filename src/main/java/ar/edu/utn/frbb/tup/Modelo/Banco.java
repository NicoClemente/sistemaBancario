package ar.edu.utn.frbb.tup.Modelo;

import java.util.ArrayList;
import java.util.List;

public class Banco {
    private List<Cliente> clientes;

    public Banco() {
        this.clientes = new ArrayList<>();
    }

    public void agregarCliente(Cliente cliente) {
        this.clientes.add(cliente);
    }

    public void eliminarCliente(Cliente cliente) {
        this.clientes.remove(cliente);
    }

    public Cliente buscarClientePorDni(String dni) {
        for (Cliente cliente : this.clientes) {
            if (cliente.getDni().equals(dni)) {
                return cliente;
            }
        }
        return null;
    }

    public List<Cliente> getClientes() {
        return this.clientes;
    }
}