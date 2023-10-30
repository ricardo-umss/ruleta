public class ApuestaColor extends Apuesta {
    private String color;
    
    public ApuestaColor(double monto, String color) {
        super(monto);
        this.color = color;
    }
    
    
    @Override
    public boolean ganoApuesta(int numeroGanador) {
        String colorNumeroGanador = getColor(numeroGanador);
        return colorNumeroGanador.equals(color);
    }

    @Override
    public double calcularGanancia(int numeroGanador) {
        double ganancia = 0;
        if (ganoApuesta(numeroGanador)&& numeroGanador!=0) {
            ganancia = monto * 2; 
        }
        return ganancia;
    }
    @Override
    public String mostrarApuesta() {
        return monto +" "+ color;
    }
    private String getColor(int numero) {
        if (numero % 2 == 0) {
            return "rojo";
        } else {
            return "negro"; 
        }
    }
}
