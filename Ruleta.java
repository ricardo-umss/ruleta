import java.util.Random;

public class Ruleta {
    // simular giro
    public int girar() {
        Random random = new Random();
        int intento = random.nextInt(200)+1;
        System.out.println("Ruleta girando.");
        int posicion=1;
        while(intento>0){
            posicion= posicion%37;
            System.out.print(posicion+" ");
            ++posicion;
            --intento;
        }   
        posicion--;
        System.out.println();
        System.out.println("Ruleta se detuvo.");
        return posicion;
    }
}
