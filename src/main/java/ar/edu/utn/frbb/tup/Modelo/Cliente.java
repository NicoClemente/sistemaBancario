package ar.edu.utn.frbb.tup.Modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Persona {
    private TipoPersona tipoPersona;
    private List<Cuenta> cuentas;

    public Cliente(String nombre, String apellido, String dni, LocalDate fechaNacimiento, String direccion, String telefono, String email, TipoPersona tipoPersona) {
        super(nombre, apellido, dni, fechaNacimiento, direccion, telefono, email);
        this.tipoPersona = tipoPersona;
        this.cuentas = new ArrayList<>();
    }

    public TipoPersona getTipoPersona() {
        return this.tipoPersona;
    }

    public void setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public List<Cuenta> getCuentas() {
        return this.cuentas;
    }

    public void agregarCuenta(Cuenta cuenta) {
        this.cuentas.add(cuenta);
    }

    public void eliminarCuenta(Cuenta cuenta) {
        this.cuentas.remove(cuenta);
    }

    @Override
    public String toString() {
        return super.toString() + ", Tipo de Persona: " + this.tipoPersona;
    }
}