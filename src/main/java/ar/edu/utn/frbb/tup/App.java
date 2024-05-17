package ar.edu.utn.frbb.tup;

import ar.edu.utn.frbb.tup.inputProcessor.MenuInputProcessor;
import ar.edu.utn.frbb.tup.Modelo.Banco;

public class App {
    public static void main(String[] args) {
        Banco banco = new Banco();
        MenuInputProcessor menuInputProcessor = new MenuInputProcessor(banco);
        menuInputProcessor.mostrarMenu();
    }
}