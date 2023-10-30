public class ApuestaNumero extends Apuesta {
    private int numero;

    public ApuestaNumero(double monto, int numero) {
        super(monto);
        this.numero = numero;
    }
    
    @Override
    public double calcularGanancia(int numeroGanador) {
        double ganancia = 0;
        if (numero == numeroGanador) {
            ganancia = monto * 36;
        }
        return ganancia;
    }
    @Override
    public boolean ganoApuesta(int numeroGanador) {
        return numero == numeroGanador;
    }
    @Override
    public String mostrarApuesta() {
        return monto +" "+ numero;
    }
}