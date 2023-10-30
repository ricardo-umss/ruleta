public class ApuestaDocena extends Apuesta {
    private int docena;

    public ApuestaDocena(double monto, int docena) {
        super(monto);
        this.docena = docena;
    }
    
    @Override
    public double calcularGanancia(int numeroGanador) {
        double ganancia = 0;
        if (ganoApuesta(numeroGanador) && numeroGanador!=0) {
            ganancia = monto * 3;
        }
        return ganancia;
    }
    @Override
    public boolean ganoApuesta(int numeroGanador) {
         return (numeroGanador >= (docena - 1) * 12 + 1) && (numeroGanador <= docena * 12);
    }
    @Override
    public String mostrarApuesta() {
        return monto +" "+ docena;
    }
}