import java.util.List;
import java.util.ArrayList;

public class JuegoRuleta extends Ruleta implements IGestionApuestas {
    private List<Apuesta> apuestas = new ArrayList<>();
    private double limiteApuesta = 100; // Límite de apuesta
    private Jugador jugadorActual;

    public JuegoRuleta(Jugador jugador) {
        
        jugadorActual = jugador;
    }

    @Override
    public void realizarApuesta(Apuesta apuesta) {
        if (apuesta.getMonto() <= jugadorActual.obtenerSaldo() && apuesta.getMonto() <= limiteApuesta) {
            apuestas.add(apuesta);
            jugadorActual.establecerSaldo(jugadorActual.obtenerSaldo()-apuesta.getMonto());
        } else {
            System.out.println("Apuesta no válida: saldo insuficiente o excede el límite de apuesta.");
        }
    }

    @Override
    public void pagarGanancias(int resultado) {
        for (Apuesta apuesta : apuestas) {            
            double ganancia = apuesta.calcularGanancia(resultado);
            jugadorActual.establecerSaldo(jugadorActual.obtenerSaldo()+ganancia);
            RegistroHistorial.registrarApuesta(jugadorActual.getID(), apuesta.getTipoApuesta(), apuesta.mostrarApuesta(), apuesta.getMonto());   
            if(apuesta.ganoApuesta(resultado)){
                System.out.println("¡Has ganado " + ganancia );
            }
            else{
                System.out.println("¡Has perdido " + apuesta.getMonto());
            }
        }
        System.out.println("Saldo actual: " + jugadorActual.obtenerSaldo());

        apuestas.clear();
    }
    
    public void verEstadoMesa() {
        System.out.println("Estado actual de la mesa y apuestas:");
        for(int i = 0; i<apuestas.size();i++){
            System.out.println("Apuesta #"+(i+1)+": "+apuestas.get(i).mostrarApuesta());
        }
    }
}
