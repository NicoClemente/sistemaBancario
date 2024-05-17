package ar.edu.utn.frbb.tup.Modelo;

import java.time.LocalDate;

public abstract class Persona {
    private String nombre;
    private String apellido;
    private String dni;
    private LocalDate fechaNacimiento;
    private String direccion;
    private String telefono;
    private String email;

    public Persona(String nombre, String apellido, String dni, LocalDate fechaNacimiento, String direccion, String telefono, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return this.dni;
    }

    public LocalDate getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreCompleto() {
        return this.nombre + " " + this.apellido;
    }

    @Override
    public String toString() {
        return "Nombre: " + this.nombre + ", Apellido: " + this.apellido + ", DNI: " + this.dni + ", Fecha de Nacimiento: " + this.fechaNacimiento + ", Dirección: " + this.direccion + ", Teléfono: " + this.telefono + ", Email: " + this.email;
    }
}