package ar.edu.utn.frbb.tup.inputProcessor;

import ar.edu.utn.frbb.tup.Modelo.Banco;
import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.Cuenta;
import ar.edu.utn.frbb.tup.Modelo.Movimiento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class MenuInputProcessor {
    private Scanner scanner;
    private Banco banco;
    private ClienteInputProcessor clienteInputProcessor;
    private CuentaInputProcessor cuentaInputProcessor;
    private MovimientoInputProcessor movimientoInputProcessor;
    private ClienteRepositorioImpl clienteRepositorio;
    private CuentaRepositorioImpl cuentaRepositorio;
    private MovimientoRepositorioImpl movimientoRepositorio;

    public MenuInputProcessor(Banco banco) {
        this.scanner = new Scanner(System.in);
        this.banco = banco;
        this.clienteInputProcessor = new ClienteInputProcessor();
        this.cuentaInputProcessor = new CuentaInputProcessor();
        this.movimientoInputProcessor = new MovimientoInputProcessor();
        this.clienteRepositorio = new ClienteRepositorioImpl();
        this.cuentaRepositorio = new CuentaRepositorioImpl();
        this.movimientoRepositorio = new MovimientoRepositorioImpl();
        cargarDatos();
        }
        private void cargarDatos() {
            List<Cliente> clientes = clienteRepositorio.todos();
            for (Cliente cliente : clientes) {
                banco.agregarCliente(cliente);
            }
        
            List<Cuenta> cuentas = cuentaRepositorio.todas();
            for (Cuenta cuenta : cuentas) {
                Cliente cliente = cuenta.getCliente();
                if (cliente != null) {
                    String dniCliente = cliente.getDni();
                    Cliente clienteEncontrado = banco.buscarClientePorDni(dniCliente);
                    if (clienteEncontrado != null) {
                        clienteEncontrado.agregarCuenta(cuenta);
                    }
                }
            }
        
            List<Movimiento> movimientos = movimientoRepositorio.todos();
            for (Movimiento movimiento : movimientos) {
                Cuenta cuenta = movimiento.getCuenta();
                if (cuenta != null) {
                    String numeroCuenta = cuenta.getNumero();
                    Cuenta cuentaEncontrada = buscarCuentaPorNumero(numeroCuenta);
                    if (cuentaEncontrada != null) {
                        cuentaEncontrada.agregarMovimiento(movimiento);
                    }
                }
            }
        }
        
        public void mostrarMenu() {
            int opcion;
            do {
                System.out.println("----- MENÚ PRINCIPAL -----");
                System.out.println("1. Gestionar Clientes");
                System.out.println("2. Gestionar Cuentas");
                System.out.println("3. Realizar Movimientos");
                System.out.println("0. Salir");
                System.out.print("Ingrese una opción: ");
                opcion = Integer.parseInt(scanner.nextLine());
        
                switch (opcion) {
                    case 1:
                        gestionarClientes();
                        break;
                    case 2:
                        gestionarCuentas();
                        break;
                    case 3:
                        realizarMovimientos();
                        break;
                    case 0:
                        System.out.println("¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } while (opcion != 0);
        }
        
        private void gestionarClientes() {
            int opcion;
            do {
                System.out.println("----- GESTIONAR CLIENTES -----");
                System.out.println("1. Crear Cliente");
                System.out.println("2. Modificar Cliente");
                System.out.println("3. Eliminar Cliente");
                System.out.println("4. Listar Clientes");
                System.out.println("0. Volver al Menú Principal");
                System.out.print("Ingrese una opción: ");
                opcion = Integer.parseInt(scanner.nextLine());
        
                switch (opcion) {
                    case 1:
                        Cliente nuevoCliente = clienteInputProcessor.ingresarCliente();
                        banco.agregarCliente(nuevoCliente);
                        System.out.println("Cliente creado exitosamente.");
                        break;
                    case 2:
                        System.out.print("Ingrese el DNI del cliente a modificar: ");
                        String dniModificar = scanner.nextLine();
                        Cliente clienteModificar = banco.buscarClientePorDni(dniModificar);
                        if (clienteModificar != null) {
                            clienteInputProcessor.modificarCliente(clienteModificar);
                            System.out.println("Cliente modificado exitosamente.");
                        } else {
                            System.out.println("Cliente no encontrado.");
                        }
                        break;
                    case 3:
                        System.out.print("Ingrese el DNI del cliente a eliminar: ");
                        String dniEliminar = scanner.nextLine();
                        Cliente clienteEliminar = banco.buscarClientePorDni(dniEliminar);
                        if (clienteEliminar != null) {
                            banco.eliminarCliente(clienteEliminar);
                            clienteRepositorio.eliminar(clienteEliminar);
        
                            // Eliminar las cuentas asociadas al cliente eliminado
                            List<Cuenta> cuentasEliminadas = cuentaRepositorio.buscarPorCliente(dniEliminar);
                            for (Cuenta cuenta : cuentasEliminadas) {
                                cuentaRepositorio.eliminar(cuenta);
                            }
        
                            System.out.println("Cliente eliminado exitosamente.");
                        } else {
                            System.out.println("Cliente no encontrado.");
                        }
                        break;
                    case 4:
                        List<Cliente> clientes = banco.getClientes();
                        if (clientes.isEmpty()) {
                            System.out.println("No hay clientes registrados.");
                        } else {
                            System.out.println("Lista de Clientes:");
                            System.out.println("--------------------");
                            for (Cliente cliente : clientes) {
                                System.out.println(cliente);
                                System.out.println("--------------------");
                            }
                        }
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } while (opcion != 0);
        }
        
        private void gestionarCuentas() {
            int opcion;
            do {
                System.out.println("----- GESTIONAR CUENTAS -----");
                System.out.println("1. Crear Cuenta");
                System.out.println("2. Modificar Cuenta");
                System.out.println("3. Eliminar Cuenta");
                System.out.println("4. Listar Cuentas");
                System.out.println("0. Volver al Menú Principal");
                System.out.print("Ingrese una opción: ");
                opcion = Integer.parseInt(scanner.nextLine());
        
                switch (opcion) {
                    case 1:
                        System.out.print("Ingrese el DNI del cliente: ");
                        String dniCrear = scanner.nextLine();
                        Cliente clienteCrear = banco.buscarClientePorDni(dniCrear);
                        if (clienteCrear != null) {
                            Cuenta nuevaCuenta = cuentaInputProcessor.ingresarCuenta(clienteCrear);
                            clienteCrear.agregarCuenta(nuevaCuenta);
                            System.out.println("Cuenta creada exitosamente.");
                        } else {
                            System.out.println("Cliente no encontrado.");
                        }
                        break;
                    case 2:
                        System.out.print("Ingrese el número de cuenta a modificar: ");
                        String numeroModificar = scanner.nextLine();
                        Cuenta cuentaModificar = buscarCuentaPorNumero(numeroModificar);
                        if (cuentaModificar != null) {
                            cuentaInputProcessor.modificarCuenta(cuentaModificar);
                            System.out.println("Cuenta modificada exitosamente.");
                        } else {
                            System.out.println("Cuenta no encontrada.");
                        }
                        break;
                    case 3:
                        System.out.print("Ingrese el número de cuenta a eliminar: ");
                        String numeroEliminar = scanner.nextLine();
                        Cuenta cuentaEliminar = buscarCuentaPorNumero(numeroEliminar);
                        if (cuentaEliminar != null) {
                            cuentaEliminar.getCliente().eliminarCuenta(cuentaEliminar);
                            cuentaRepositorio.eliminar(cuentaEliminar);
                            System.out.println("Cuenta eliminada exitosamente.");
                        } else {
                            System.out.println("Cuenta no encontrada.");
                        }
                        break;
                    case 4:
                        List<Cliente> clientes = banco.getClientes();
                        if (clientes.isEmpty()) {
                            System.out.println("No hay clientes registrados.");
                        } else {
                            System.out.println("Lista de Cuentas:");
                            System.out.println("--------------------");
                            for (Cliente cliente : clientes) {
                                System.out.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellido());
                                List<Cuenta> cuentas = cliente.getCuentas();
                                for (Cuenta cuenta : cuentas) {
                                    System.out.println(cuenta);
                                    System.out.println("--------------------");
                                }
                                System.out.println();
                            }
                        }
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } while (opcion != 0);
        }
        
        private void realizarMovimientos() {
            int opcion;
            do {
                System.out.println("----- REALIZAR MOVIMIENTOS -----");
                System.out.println("1. Depósito");
                System.out.println("2. Retiro");
                System.out.println("3. Transferencia");
                System.out.println("0. Volver al Menú Principal");
                System.out.print("Ingrese una opción: ");
                opcion = Integer.parseInt(scanner.nextLine());
        
                switch (opcion) {
                    case 1:
                        System.out.print("Ingrese el número de cuenta: ");
                        String numeroDeposito = scanner.nextLine();
                        Cuenta cuentaDeposito = buscarCuentaPorNumero(numeroDeposito);
                        if (cuentaDeposito != null) {
                            Movimiento movimientoDeposito = movimientoInputProcessor.ingresarMovimiento(cuentaDeposito, "DEPOSITO");
                            cuentaDeposito.agregarMovimiento(movimientoDeposito);
                            System.out.println("Depósito realizado exitosamente.");
                            System.out.println("--------------------");
                            cuentaRepositorio.guardar(cuentaDeposito);
                            movimientoRepositorio.guardar(movimientoDeposito);
                        } else {
                            System.out.println("Cuenta no encontrada.");
                        }
                        break;
                    case 2:
                        System.out.print("Ingrese el número de cuenta: ");
                        String numeroRetiro = scanner.nextLine();
                        Cuenta cuentaRetiro = buscarCuentaPorNumero(numeroRetiro);
                        if (cuentaRetiro != null) {
                            Movimiento movimientoRetiro = movimientoInputProcessor.ingresarMovimiento(cuentaRetiro, "RETIRO");
                            if (cuentaRetiro.retirar(movimientoRetiro.getMonto())) {
                                cuentaRetiro.agregarMovimiento(movimientoRetiro);
                                System.out.println("Retiro realizado exitosamente.");
                                System.out.println("--------------------");
                                cuentaRepositorio.guardar(cuentaRetiro);
                                movimientoRepositorio.guardar(movimientoRetiro);
                            } else {
                                System.out.println("Saldo insuficiente.");
                            }
                        } else {
                            System.out.println("Cuenta no encontrada.");
                        }
                        break;
                    case 3:
                        System.out.print("Ingrese el número de cuenta origen: ");
                        String numeroOrigen = scanner.nextLine();
                        Cuenta cuentaOrigen = buscarCuentaPorNumero(numeroOrigen);
                        if (cuentaOrigen != null) {
                            System.out.print("Ingrese el número de cuenta destino: ");
                            String numeroDestino = scanner.nextLine();
                            Cuenta cuentaDestino = buscarCuentaPorNumero(numeroDestino);
                            if (cuentaDestino != null) {
                                System.out.print("Ingrese el monto a transferir: ");
                                double montoTransferencia = Double.parseDouble(scanner.nextLine());
                                if (cuentaOrigen.transferir(cuentaDestino, montoTransferencia)) {
                                    System.out.println("Transferencia realizada exitosamente.");
                                    System.out.println("--------------------");
        
                                    // Guardar las cuentas modificadas
                                    cuentaRepositorio.guardar(cuentaOrigen);
                                    cuentaRepositorio.guardar(cuentaDestino);
        
                                    // Registrar el movimiento
                                    Movimiento movimientoOrigen = new Movimiento("TRANSFERENCIA_SALIDA", montoTransferencia,
                                            LocalDateTime.now(), cuentaOrigen);
                                    Movimiento movimientoDestino = new Movimiento("TRANSFERENCIA_ENTRADA",
                                            montoTransferencia, LocalDateTime.now(), cuentaDestino);
                                    movimientoRepositorio.guardar(movimientoOrigen);
                                    movimientoRepositorio.guardar(movimientoDestino);
                                } else {
                                    System.out.println("Saldo insuficiente en la cuenta origen.");
                                }
                            } else {
                                System.out.println("Cuenta destino no encontrada.");
                            }
                        } else {
                            System.out.println("Cuenta origen no encontrada.");
                        }
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } while (opcion != 0);
        }
        
        private Cuenta buscarCuentaPorNumero(String numero) {
            List<Cliente> clientes = banco.getClientes();
            for (Cliente cliente : clientes) {
                List<Cuenta> cuentas = cliente.getCuentas();
                for (Cuenta cuenta : cuentas) {
                    if (cuenta.getNumero().equals(numero)) {
                        return cuenta;
                    }
                }
            }
            return null;
        }
    }