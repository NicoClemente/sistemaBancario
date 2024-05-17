package ar.edu.utn.frbb.tup.inputProcessor;

import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.Cuenta;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CuentaRepositorioImpl {
    private static final String ARCHIVO = "Data/cuentas.csv";
    private static final String SEPARADOR = ",";
    private ClienteRepositorioImpl clienteRepositorio;

    public CuentaRepositorioImpl() {
        this.clienteRepositorio = new ClienteRepositorioImpl();
    }

    public void guardar(Cuenta cuenta) {
        List<String> lineas = leerArchivo();
        int index = -1;
        for (int i = 0; i < lineas.size(); i++) {
            if (lineas.get(i).split(SEPARADOR)[0].equals(cuenta.getNumero())) {
                index = i;
                break;
            }
        }

        String lineaCuenta = cuenta.getNumero() + SEPARADOR + cuenta.getTipo() + SEPARADOR + cuenta.getSaldo() + SEPARADOR + cuenta.getFechaCreacion() + SEPARADOR + cuenta.getCliente().getDni();

        if (index == -1) {
            lineas.add(lineaCuenta);
        } else {
            lineas.set(index, lineaCuenta);
        }

        escribirArchivo(lineas);
    }

    public void eliminar(Cuenta cuenta) {
        List<String> lineas = leerArchivo();
        lineas.removeIf(linea -> linea.split(SEPARADOR)[0].equals(cuenta.getNumero()));
        escribirArchivo(lineas);
    }

    public Cuenta buscarPorNumero(String numero) {
        List<String> lineas = leerArchivo();
        for (String linea : lineas) {
            String[] campos = linea.split(SEPARADOR);
            if (campos[0].equals(numero)) {
                String tipo = campos[1];
                double saldo = Double.parseDouble(campos[2]);
                LocalDateTime fechaCreacion = LocalDateTime.parse(campos[3]);
                String dniCliente = campos[4];
                Cliente cliente = clienteRepositorio.buscarPorDni(dniCliente);
                return new Cuenta(numero, tipo, saldo, cliente);
            }
        }
        return null;
    }

    public List<Cuenta> buscarPorCliente(String dniCliente) {
        List<Cuenta> cuentas = new ArrayList<>();
        List<String> lineas = leerArchivo();
        for (String linea : lineas) {
            String[] campos = linea.split(SEPARADOR);
            if (campos[4].equals(dniCliente)) {
                String numero = campos[0];
                String tipo = campos[1];
                double saldo = Double.parseDouble(campos[2]);
                Cliente cliente = clienteRepositorio.buscarPorDni(dniCliente);
                Cuenta cuenta = new Cuenta(numero, tipo, saldo, cliente);
                cuentas.add(cuenta);
            }
        }
        return cuentas;
    }

    public List<Cuenta> todas() {
        List<Cuenta> cuentas = new ArrayList<>();
        List<String> lineas = leerArchivo();
        for (String linea : lineas) {
            String[] campos = linea.split(SEPARADOR);
            String numero = campos[0];
            String tipo = campos[1];
            double saldo = Double.parseDouble(campos[2]);
            LocalDateTime fechaCreacion = LocalDateTime.parse(campos[3]);
            String dniCliente = campos[4];
            Cliente cliente = clienteRepositorio.buscarPorDni(dniCliente);
            Cuenta cuenta = new Cuenta(numero, tipo, saldo, cliente);
            cuentas.add(cuenta);
        }
        return cuentas;
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
            writer.write("numero,tipo,saldo,fechaCreacion,dniCliente");
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