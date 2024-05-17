package ar.edu.utn.frbb.tup.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UtilidadesFecha {
    public static String formatearFecha(LocalDate fecha) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fecha.format(formato);
    }
}