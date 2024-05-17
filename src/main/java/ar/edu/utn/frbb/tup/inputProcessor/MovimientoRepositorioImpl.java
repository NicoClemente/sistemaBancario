package ar.edu.utn.frbb.tup.inputProcessor;

import ar.edu.utn.frbb.tup.Modelo.Cuenta;
import ar.edu.utn.frbb.tup.Modelo.Movimiento;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MovimientoRepositorioImpl {
    private static final String ARCHIVO = "Data/movimientos.csv";
    private static final String SEPARADOR = ",";
    private CuentaRepositorioImpl cuentaRepositorio;

    public MovimientoRepositorioImpl() {
        this.cuentaRepositorio = new CuentaRepositorioImpl();
    }

    public void guardar(Movimiento movimiento) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            String linea = movimiento.getTipo() + SEPARADOR +
                    movimiento.getMonto() + SEPARADOR +
                    movimiento.getFecha() + SEPARADOR +
                    movimiento.getCuenta().getNumero();
            writer.write(linea);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Movimiento> buscarPorCuenta(String numeroCuenta) {
        List<Movimiento> movimientos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] campos = linea.split(SEPARADOR);
                if (campos[3].equals(numeroCuenta)) {
                    String tipo = campos[0];
                    double monto = Double.parseDouble(campos[1]);
                    LocalDateTime fecha = LocalDateTime.parse(campos[2]);
                    Cuenta cuenta = cuentaRepositorio.buscarPorNumero(numeroCuenta);
                    Movimiento movimiento = new Movimiento(tipo, monto, fecha, cuenta);
                    movimientos.add(movimiento);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movimientos;
    }

    public List<Movimiento> todos() {
        List<Movimiento> movimientos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            boolean primeraLinea = true;
            while ((linea = reader.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue; 
                }
                String[] campos = linea.split(SEPARADOR);
                String tipo = campos[0];
                double monto = Double.parseDouble(campos[1]);
                LocalDateTime fecha = LocalDateTime.parse(campos[2]);
                String numeroCuenta = campos[3];
                Cuenta cuenta = cuentaRepositorio.buscarPorNumero(numeroCuenta);
                Movimiento movimiento = new Movimiento(tipo, monto, fecha, cuenta);
                movimientos.add(movimiento);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movimientos;
    }
}