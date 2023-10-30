public abstract class Apuesta {
    protected double monto;

    public Apuesta(double monto) {
        if (monto >= 0) {
            this.monto = monto;
        } else {
            throw new IllegalArgumentException("El monto de la apuesta no puede ser negativo.");
        }
    }

    public double getMonto() {
        return monto;
    }
    
    public void setMonto(double monto) {
        this.monto = monto;
    }
    
    public String getTipoApuesta(){
        return this.getClass().toString().split(" ")[1];
    }
    
    public abstract double calcularGanancia(int numeroGanador);
    public abstract boolean ganoApuesta(int numeroGanador);
    public abstract String mostrarApuesta();
}