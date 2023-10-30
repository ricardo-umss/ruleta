import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Jugador {
    private String nombre;
    private String apodo;
    private double saldo;
    private int id;

    public Jugador(int id, String nombre, String apodo, double saldo) {
        if (nombre != null && esNombreValido(nombre) &&
            apodo != null && esApodoValido(apodo) &&
            saldo >= 0 && id > 0) {
            this.nombre = nombre;
            this.apodo = apodo;
            this.saldo = saldo;
            this.id = id;
        } else {
            throw new IllegalArgumentException("Valores de jugador no válidos");
        }
    }

    public String obtenerNombre() {
        return nombre;
    }

    public void establecerNombre(String nombre) {
        if (nombre != null && esNombreValido(nombre)) {
            this.nombre = nombre;
        }
    }

    public String obtenerApodo() {
        return apodo;
    }

    public void establecerApodo(String apodo) {
        if (apodo != null && esApodoValido(apodo)) {
            this.apodo = apodo;
        }
    }

    public double obtenerSaldo() {
        return saldo;
    }

    public void establecerSaldo(double saldo) {
        if (saldo >= 0) {
            this.saldo = saldo;
        }
    }
    
    public int getID() {
        return id;
    }

    private boolean esNombreValido(String nombre) {
        return Pattern.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ]+$", nombre);
    }

    private boolean esApodoValido(String apodo) {
        return Pattern.matches("^[a-zA-Z0-9]+$", apodo);
    }
}
