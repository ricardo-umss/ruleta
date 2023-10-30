import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
public class Prueba {
    private static final int TIEMPO_APUESTA_SEGUNDOS = 60;
    public static void main(String[] args) {
        // Datos de autenticación del jugador
        String correo = "ricardo@gmail.com";
        String contrasena = "contraseña123";

        // Intenta autenticar al jugador utilizando la clase Autenticacion
        Map<String, Object> datosJugador = Autenticacion.autenticar(correo, contrasena);

        if (datosJugador != null) {
            String nombre = (String) datosJugador.get("nombre");
            String apodo = (String) datosJugador.get("apodo");
            double saldo = (double) datosJugador.get("saldo");
            int id = (int) datosJugador.get("id");

            System.out.println("Jugador autenticado:");
            System.out.println("Nombre: " + nombre);
            System.out.println("Apodo: " + apodo);
            System.out.println("Saldo: " + saldo);
            System.out.println("________________________");

            // Crea una instancia de JuegoRuleta con el jugador autenticado
            Jugador jugador = new Jugador(id,nombre, apodo, saldo);
            JuegoRuleta juego = new JuegoRuleta(jugador);

            // Realiza algunas apuestas
            Apuesta apuestaNumero = new ApuestaNumero(10, 23);
            System.out.println("Apuesta numero 23, cantidad apuesta 10");
            Apuesta apuestaRojo = new ApuestaColor(5, "Rojo");
            System.out.println("Apuesta color rojo, cantidad apuesta 5");
            Apuesta apuestaDocena = new ApuestaDocena(15, 1);
            System.out.println("Apuesta docena 1, cantidad apuesta 15");

            juego.realizarApuesta(apuestaNumero);
            juego.realizarApuesta(apuestaRojo);
            juego.realizarApuesta(apuestaDocena);

            // Muestra el estado de la mesa con las apuestas actuales
            juego.verEstadoMesa();

            // Realiza el giro de la ruleta y muestra el resultado
            int resultado = juego.girar();
            System.out.println("Número ganador: " + resultado);

            // Paga las ganancias y muestra los cambios en el saldo
            juego.pagarGanancias(resultado);
        } else {
            System.out.println("Autenticación fallida. El jugador no se encontró en la base de datos.");
        }
    }

    public static void jugar(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Bienvenido al juego de ruleta.");
        System.out.println("Por favor, inicie sesión.");

        System.out.print("Correo electrónico: ");
        String correo = scanner.nextLine();

        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();
        JuegoRuleta juego;
        // Intenta autenticar al jugador utilizando la clase Autenticacion
        Map<String, Object> datosJugador = Autenticacion.autenticar(correo, contrasena);

        if (datosJugador != null) {
            String nombre = (String) datosJugador.get("nombre");
            String apodo = (String) datosJugador.get("apodo");
            double saldo = (double) datosJugador.get("saldo");
            int id = (int) datosJugador.get("id");

            System.out.println("Jugador autenticado:");
            System.out.println("Nombre: " + nombre);
            System.out.println("Apodo: " + apodo);
            System.out.println("Saldo: " + saldo);
            System.out.println("________________________");

            // Crea una instancia de JuegoRuleta con el jugador autenticado
            Jugador jugador = new Jugador(id, nombre, apodo, saldo);
            juego = new JuegoRuleta(jugador);

            // Hilo de temporizador para el tiempo de apuesta
            CountDownLatch temporizadorLatch = new CountDownLatch(1);
            Thread temporizadorThread = new Thread(() -> {
                try {
                    for (int tiempo = TIEMPO_APUESTA_SEGUNDOS; tiempo > 0; tiempo--) {
                        System.out.println("Tiempo restante de apuesta: " + tiempo + " segundos");
                        Thread.sleep(1000); // Espera 1 segundo
                    }
                    System.out.println("El tiempo de apuesta ha terminado.");
                    scanner.close();
                    temporizadorLatch.countDown(); // Notifica que el tiempo ha expirado
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            temporizadorThread.start();

            while (true) {
                if (temporizadorLatch.getCount() == 0) {
                    System.out.println("Ya no se pueden hacer más apuestas. Girando la ruleta...");
                    int resultado = juego.girar();
                    System.out.println("Número ganador: " + resultado);
                    juego.pagarGanancias(resultado);
                    break;
                }

                System.out.println("Elige una opción:");
                System.out.println("1. Realizar apuesta");
                System.out.println("2. Ver estado de la mesa");
                System.out.println("3. Salir");

                int opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        // Verificar si el tiempo de apuesta aún no ha expirado
                        if (temporizadorLatch.getCount() > 0) {
                            System.out.println("Elige el tipo de apuesta opción:");
                            System.out.println("1. Apuesta numerica");
                            System.out.println("2. Apuesta por color");
                            System.out.println("3. Apuesta por docena");
                            System.out.println("4. Salir");
                            int opcion2= scanner.nextInt();
                            switch (opcion2) {
                                case 1:
                                    // Verificar si el tiempo de apuesta aún no ha expirado
                                    if (temporizadorLatch.getCount() > 0) {
                                        System.out.println("Elige el un numero para tu apuesta:");
                                        int numero = scanner.nextInt();
                                        System.out.println("Ingresa el monto de tu apuesta:");
                                        int monto = scanner.nextInt();
                                        System.out.println("Tu apuesta es la siguiente:");
                                        System.out.println("Tipo apuesta: Apuesta Numerica");
                                        System.out.println("Numero elegido: "+numero);
                                        System.out.println("Monto apostado: "+monto);
                                        Apuesta a = new ApuestaNumero(numero, monto);
                                        if (temporizadorLatch.getCount() > 0) {
                                            juego.realizarApuesta(a);
                                            System.out.println("Apuesta realizada.");
                                        }
                                        else{
                                            System.out.println("El tiempo de apuesta ha terminado. No se pudo realizar la apuesta.");
                                        }
                                    } else {
                                        System.out.println("El tiempo de apuesta ha terminado. No se pueden realizar más apuestas.");
                                    }
                                    break;
                                case 2:
                                    // Verificar si el tiempo de apuesta aún no ha expirado
                                    if (temporizadorLatch.getCount() > 0) {
                                        System.out.println("Elige el color para tu apuesta:");
                                        System.out.println("1. Color Rojo");
                                        System.out.println("2. Color Negro");
                                        int numero = scanner.nextInt();
                                        String color = "Rojo";
                                        if(numero==2){
                                            color = "Negro";
                                        }
                                        System.out.println("Ingresa el monto de tu apuesta:");
                                        int monto = scanner.nextInt();
                                        System.out.println("Tu apuesta es la siguiente:");
                                        System.out.println("Tipo apuesta: Apuesta Color");
                                        System.out.println("Color elegido: "+color);
                                        System.out.println("Monto apostado: "+monto);
                                        Apuesta a = new ApuestaColor(monto, color);
                                        if (temporizadorLatch.getCount() > 0) {
                                            juego.realizarApuesta(a);
                                            System.out.println("Apuesta realizada.");
                                        }
                                        else{
                                            System.out.println("El tiempo de apuesta ha terminado. No se pudo realizar la apuesta.");
                                        }
                                    } else {
                                        System.out.println("El tiempo de apuesta ha terminado. No se pueden realizar más apuestas.");
                                    }
                                    break;
                                case 3:
                                    // Verificar si el tiempo de apuesta aún no ha expirado
                                    if (temporizadorLatch.getCount() > 0) {
                                        System.out.println("Elige la docena para tu apuesta:");
                                        System.out.println("1. 1ra Docena 1-12");
                                        System.out.println("2. 2da Docena 13-24");
                                        System.out.println("3. 3ra Docena 15-36");
                                        int numero = scanner.nextInt();
                                        System.out.println("Ingresa el monto de tu apuesta:");
                                        int monto = scanner.nextInt();
                                        System.out.println("Tu apuesta es la siguiente:");
                                        System.out.println("Tipo apuesta: Apuesta Color");
                                        System.out.println("Docena elegida: "+numero+"º");
                                        System.out.println("Monto apostado: "+monto);
                                        Apuesta a = new ApuestaDocena(monto, numero);
                                        if (temporizadorLatch.getCount() > 0) {
                                            juego.realizarApuesta(a);
                                            System.out.println("Apuesta realizada.");
                                        }
                                        else{
                                            System.out.println("El tiempo de apuesta ha terminado. No se pudo realizar la apuesta.");
                                        }
                                    } else {
                                        System.out.println("El tiempo de apuesta ha terminado. No se pueden realizar más apuestas.");
                                    }
                                    break;
                                default:
                                    System.out.println("Opción no válida. Introduce un número válido.");
                                    break;
                            }
                        } else {
                            System.out.println("El tiempo de apuesta ha terminado. No se pueden realizar más apuestas.");
                        }
                        break;
                    case 2:
                        juego.verEstadoMesa();
                        break;
                    case 3:
                        System.out.println("Saliendo del juego.");
                        temporizadorThread.interrupt(); // Detener el temporizador
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción no válida. Introduce un número válido.");
                        break;
                }
            }
        } else {
            System.out.println("Autenticación fallida. El jugador no se encontró en la base de datos.");
        }
    }

}
