import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class RegistroHistorial {
    public static void registrarApuesta(int jugadorId, String tipoApuesta, String apuesta, double monto) {
        // Validar los datos de la apuesta, si es necesario
        boolean apuestaValida = validarApuesta(tipoApuesta, apuesta, monto);

        if (apuestaValida) {
            // Continuar con el proceso de registro en la base de datos
            String consulta = "INSERT INTO historial_apuestas (jugador_id, tipo_apuesta, apuesta, monto, fecha_apuesta) VALUES (?, ?, ?, ?, ?)";

            try (Connection conexion = ConexionBD.obtenerConexion();
                 PreparedStatement statement = conexion.prepareStatement(consulta)) {
                statement.setInt(1, jugadorId);
                statement.setString(2, tipoApuesta);
                statement.setString(3, apuesta);
                statement.setDouble(4, monto);
                statement.setTimestamp(5, new java.sql.Timestamp(new Date().getTime())); 
                int filasInsertadas = statement.executeUpdate();

                if (filasInsertadas > 0) {
                    System.out.println("Apuesta registrada exitosamente en historial.");
                } else {
                    System.out.println("Error al registrar la apuesta en historial.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean validarApuesta(String tipoApuesta, String apuesta, double monto) {
        
        if (monto <= 0) {
            System.out.println("Monto de apuesta no válido. La apuesta ha fallado.");
            return false;
        }

        if (!tipoApuestaValido(tipoApuesta)) {
            System.out.println("Tipo de apuesta no válido. La apuesta ha fallado.");
            return false;
        }

        return true;
    }

    public static boolean tipoApuestaValido(String tipoApuesta) {
        return true;
    }

    public static void main(String[] args) {
        // Ejemplo de registro de apuesta
        registrarApuesta(1, "TipoA", "ApuestaX", 50.0);
    }
}
