import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class RegistroJugador {    
    public static void registrarJugador(String nombre, String apodo, String correo, String contrasena, double saldoInicial) {
        boolean registroValido = true; // Variable que controla el registro

        // Realiza controles y validaciones
        if (!validarNombre(nombre)) {
            System.out.println("Nombre no válido. El registro ha fallado.");
            registroValido = false;
        }
        if (!validarApodo(apodo)) {
            System.out.println("Apodo no válido. El registro ha fallado.");
            registroValido = false;
        }
        if (!validarCorreo(correo)) {
            System.out.println("Correo no válido. El registro ha fallado.");
            registroValido = false;
        }
        if (!validarSaldo(saldoInicial)) {
            System.out.println("Saldo no válido. El registro ha fallado.");
            registroValido = false;
        }
        if (!validarContrasena(contrasena)) {
            System.out.println("Contraseña no válida. El registro ha fallado.");
            registroValido = false;
        }

        if (registroValido) {
            // Continúa con el proceso de registro
            String consulta = "INSERT INTO jugadores (nombre, apodo, correo, contrasena, saldo) VALUES (?, ?, ?, ?, ?)";

            int filasInsertadas = 0;

            try (Connection conexion = ConexionBD.obtenerConexion();
                 PreparedStatement statement = conexion.prepareStatement(consulta)) {
                statement.setString(1, nombre);
                statement.setString(2, apodo);
                statement.setString(3, correo);
                statement.setString(4, contrasena);
                statement.setDouble(5, saldoInicial);

                filasInsertadas = statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (filasInsertadas > 0) {
                System.out.println("Registro exitoso");
            } else {
                System.out.println("Error al registrar el jugador");
            }
        }
    }

    public static boolean validarNombre(String nombre) {
        return nombre != null && nombre.matches("^[a-zA-Z]+( [a-zA-Z]+)*$");
    }

    public static boolean validarApodo(String apodo) {
        return apodo != null && apodo.matches("^[a-zA-Z0-9]+$");
    }

    public static boolean validarCorreo(String correo) {
        return correo != null && Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zAZ]{2,}$", correo);
    }

    public static boolean validarSaldo(double saldo) {
        return saldo >= 0; // Suponemos que el saldo no puede ser negativo
    }

    public static boolean validarContrasena(String contrasena) {
        return contrasena != null && contrasena.length() >= 8;
    }

    public static void main(String[] args) {
        // Ejemplo de registro con validaciones
        registrarJugador("Nombre Ejemplo", "Apodo123", "correo@example.com", "contra006", 1000);
    }
}
