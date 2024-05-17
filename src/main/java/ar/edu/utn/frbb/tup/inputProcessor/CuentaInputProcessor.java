package ar.edu.utn.frbb.tup.inputProcessor;

import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.Cuenta;

import java.util.Scanner;

public class CuentaInputProcessor {
    private Scanner scanner;
    private CuentaRepositorioImpl cuentaRepositorio;

    public CuentaInputProcessor() {
        this.scanner = new Scanner(System.in);
        this.cuentaRepositorio = new CuentaRepositorioImpl();
    }

    public Cuenta ingresarCuenta(Cliente cliente) {
        System.out.println("Ingrese los datos de la cuenta:");
        System.out.print("Número de cuenta: ");
        String numero = scanner.nextLine();
        System.out.print("Tipo de cuenta: ");
        String tipo = scanner.nextLine();
        System.out.print("Saldo inicial: ");
        double saldo = Double.parseDouble(scanner.nextLine());

        Cuenta nuevaCuenta = new Cuenta(numero, tipo, saldo, cliente);
        cuentaRepositorio.guardar(nuevaCuenta);

        return nuevaCuenta;
    }

    public void modificarCuenta(Cuenta cuenta) {
        System.out.println("Ingrese los nuevos datos de la cuenta:");
        System.out.print("Tipo de cuenta (" + cuenta.getTipo() + "): ");
        String tipo = scanner.nextLine();
        if (!tipo.isEmpty()) {
            cuenta.setTipo(tipo);
        }
        cuentaRepositorio.guardar(cuenta);
    }
}