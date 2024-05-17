package ar.edu.utn.frbb.tup.inputProcessor;

import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.TipoPersona;

import java.time.LocalDate;
import java.util.Scanner;

public class ClienteInputProcessor {
    private Scanner scanner;
    private ClienteRepositorioImpl clienteRepositorio;

    public ClienteInputProcessor() {
        this.scanner = new Scanner(System.in);
        this.clienteRepositorio = new ClienteRepositorioImpl();
    }

    public Cliente ingresarCliente() {
        System.out.println("Ingrese los datos del cliente:");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("DNI: ");
        String dni = scanner.nextLine();
        System.out.print("Fecha de Nacimiento (yyyy-MM-dd): ");
        LocalDate fechaNacimiento = LocalDate.parse(scanner.nextLine());
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Tipo de Persona (FISICA/JURIDICA): ");
        TipoPersona tipoPersona = TipoPersona.valueOf(scanner.nextLine());

        Cliente nuevoCliente = new Cliente(nombre, apellido, dni, fechaNacimiento, direccion, telefono, email, tipoPersona);
        clienteRepositorio.guardar(nuevoCliente);

        return nuevoCliente;
    }

    public void modificarCliente(Cliente cliente) {
        System.out.println("Ingrese los nuevos datos del cliente:");
        System.out.print("Nombre (" + cliente.getNombre() + "): ");
        String nombre = scanner.nextLine();
        if (!nombre.isEmpty()) {
            cliente.setNombre(nombre);
        }
        System.out.print("Apellido (" + cliente.getApellido() + "): ");
        String apellido = scanner.nextLine();
        if (!apellido.isEmpty()) {
            cliente.setApellido(apellido);
        }
        System.out.print("Dirección (" + cliente.getDireccion() + "): ");
        String direccion = scanner.nextLine();
        if (!direccion.isEmpty()) {
            cliente.setDireccion(direccion);
        }
        System.out.print("Teléfono (" + cliente.getTelefono() + "): ");
        String telefono = scanner.nextLine();
        if (!telefono.isEmpty()) {
            cliente.setTelefono(telefono);
        }
        System.out.print("Email (" + cliente.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {
            cliente.setEmail(email);
        }

        clienteRepositorio.guardar(cliente);
    }
}