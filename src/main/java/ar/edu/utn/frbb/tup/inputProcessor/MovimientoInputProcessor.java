package ar.edu.utn.frbb.tup.inputProcessor;

import ar.edu.utn.frbb.tup.Modelo.Cuenta;
import ar.edu.utn.frbb.tup.Modelo.Movimiento;

import java.time.LocalDateTime;
import java.util.Scanner;

public class MovimientoInputProcessor {
    private Scanner scanner;

    public MovimientoInputProcessor() {
        this.scanner = new Scanner(System.in);
    }

    public Movimiento ingresarMovimiento(Cuenta cuenta, String tipo) {
        System.out.println("Ingrese el monto del movimiento:");
        double monto = scanner.nextDouble();
        scanner.nextLine(); // Consumir el salto de línea
        LocalDateTime fecha = LocalDateTime.now();
        return new Movimiento(tipo, monto, fecha, cuenta);
    }
}