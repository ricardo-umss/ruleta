import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Autenticacion {

    public static Map<String, Object> autenticar(String correo, String contrasena) {
        String consulta = "SELECT id, nombre, apodo, saldo FROM jugadores WHERE correo = ? AND contrasena = ?";
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        Map<String, Object> datosJugador = null;

        try {
            conexion = ConexionBD.obtenerConexion(); 
            sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, correo);
            sentencia.setString(2, contrasena);
            resultado = sentencia.executeQuery();

            if (resultado.next()) {
                String nombre = resultado.getString("nombre");
                String apodo = resultado.getString("apodo");
                double saldo = resultado.getDouble("saldo");
                int id = resultado.getInt("id");

                datosJugador = new HashMap<>();
                datosJugador.put("nombre", nombre);
                datosJugador.put("apodo", apodo);
                datosJugador.put("saldo", saldo);
                datosJugador.put("id", id);
            }
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            // Captura de excepciones SQL
            e.printStackTrace();
        }
        return datosJugador;
    }
}
