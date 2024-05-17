package ar.edu.utn.frbb.tup.Modelo;

import java.time.LocalDateTime;

public class Movimiento {
    private String tipo;
    private double monto;
    private LocalDateTime fecha;
    private Cuenta cuenta;

    public Movimiento(String tipo, double monto, LocalDateTime fecha, Cuenta cuenta) {
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = fecha;
        this.cuenta = cuenta;
    }

    public String getTipo() {
        return this.tipo;
    }

    public double getMonto() {
        return this.monto;
    }

    public LocalDateTime getFecha() {
        return this.fecha;
    }

    public Cuenta getCuenta() {
        return this.cuenta;
    }

    @Override
    public String toString() {
        return "Tipo: " + this.tipo + ", Monto: " + this.monto + ", Fecha: " + this.fecha + ", Cuenta: " + this.cuenta.getNumero();
    }
}