package ar.edu.utn.frbb.tup.inputProcessor;

import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.TipoPersona;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepositorioImpl {
    private static final String ARCHIVO = "Data/clientes.csv";
    private static final String SEPARADOR = ",";

    public void guardar(Cliente cliente) {
        List<String> lineas = leerArchivo();
        int index = -1;
        for (int i = 0; i < lineas.size(); i++) {
            if (lineas.get(i).split(SEPARADOR)[2].equals(cliente.getDni())) {
                index = i;
                break;
            }
        }

        String lineaCliente = cliente.getNombre() + SEPARADOR + cliente.getApellido() + SEPARADOR + cliente.getDni() + SEPARADOR + cliente.getFechaNacimiento() + SEPARADOR + cliente.getDireccion() + SEPARADOR + cliente.getTelefono() + SEPARADOR + cliente.getEmail() + SEPARADOR + cliente.getTipoPersona();

        if (index == -1) {
            lineas.add(lineaCliente);
        } else {
            lineas.set(index, lineaCliente);
        }

        escribirArchivo(lineas);
    }

    public void eliminar(Cliente cliente) {
        List<String> lineas = leerArchivo();
        lineas.removeIf(linea -> linea.split(SEPARADOR)[2].equals(cliente.getDni()));
        escribirArchivo(lineas);
    }

    public Cliente buscarPorDni(String dni) {
        List<String> lineas = leerArchivo();
        for (String linea : lineas) {
            String[] campos = linea.split(SEPARADOR);
            if (campos[2].equals(dni)) {
                String nombre = campos[0];
                String apellido = campos[1];
                LocalDate fechaNacimiento = LocalDate.parse(campos[3]);
                String direccion = campos[4];
                String telefono = campos[5];
                String email = campos[6];
                TipoPersona tipoPersona = TipoPersona.valueOf(campos[7]);
                return new Cliente(nombre, apellido, dni, fechaNacimiento, direccion, telefono, email, tipoPersona);
            }
        }
        return null;
    }

    public List<Cliente> todos() {
        List<Cliente> clientes = new ArrayList<>();
        List<String> lineas = leerArchivo();
        for (String linea : lineas) {
            String[] campos = linea.split(SEPARADOR);
            String nombre = campos[0];
            String apellido = campos[1];
            String dni = campos[2];
            LocalDate fechaNacimiento = LocalDate.parse(campos[3]);
            String direccion = campos[4];
            String telefono = campos[5];
            String email = campos[6];
            TipoPersona tipoPersona = TipoPersona.valueOf(campos[7]);
            Cliente cliente = new Cliente(nombre, apellido, dni, fechaNacimiento, direccion, telefono, email, tipoPersona);
            clientes.add(cliente);
        }
        return clientes;
    }

    private List<String> leerArchivo() {
        List<String> lineas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            boolean primeraLinea = true;
            while ((linea = reader.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                } else {
                    lineas.add(linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineas;
    }

    private void escribirArchivo(List<String> lineas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
            writer.write("nombre,apellido,dni,fechaNacimiento,direccion,telefono,email,tipoPersona");
            writer.newLine();
            for (String linea : lineas) {
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}