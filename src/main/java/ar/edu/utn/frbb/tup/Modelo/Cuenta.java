package ar.edu.utn.frbb.tup.Modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {
    private String numero;
    private String tipo;
    private double saldo;
    private LocalDateTime fechaCreacion;
    private Cliente cliente;
    private List<Movimiento> movimientos;

    public Cuenta(String numero, String tipo, double saldo, Cliente cliente) {
        this.numero = numero;
        this.tipo = tipo;
        this.saldo = saldo;
        this.fechaCreacion = LocalDateTime.now();
        this.cliente = cliente;
        this.movimientos = new ArrayList<>();
    }

    public String getNumero() {
        return this.numero;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getSaldo() {
        return this.saldo;
    }

    public LocalDateTime getFechaCreacion() {
        return this.fechaCreacion;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public List<Movimiento> getMovimientos() {
        return this.movimientos;
    }
    
    public void depositar(double monto) {
        this.saldo += monto;
    }


    public void agregarMovimiento(Movimiento movimiento) {
        this.movimientos.add(movimiento);
        if (movimiento.getTipo().equals("DEPOSITO")) {
            this.saldo += movimiento.getMonto();
        } else if (movimiento.getTipo().equals("RETIRO")) {
            this.saldo -= movimiento.getMonto();
        }
    }

    public boolean retirar(double monto) {
        if (monto <= this.saldo) {
            this.saldo -= monto;
            return true;
        }
        return false;
    }

    public boolean transferir(Cuenta cuentaDestino, double monto) {
        if (monto <= this.saldo) {
            this.saldo -= monto;
            cuentaDestino.depositar(monto);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Número de Cuenta: " + this.numero + ", Tipo: " + this.tipo + ", Saldo: " + String.format("%.0f", this.saldo) + ", Fecha de Creación: " + this.fechaCreacion + ", Cliente: " + this.cliente.getNombreCompleto();
    }
}