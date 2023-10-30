import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorEventos {
    public static void main(String[] args) {
        int puerto = 55555; // Puerto en el que escuchará el servidor

        try {
            ServerSocket serverSocket = new ServerSocket(puerto);
            System.out.println("Servidor en ejecución, esperando eventos...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + clientSocket.getInetAddress());

                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String evento;
                while ((evento = reader.readLine()) != null) {
                    System.out.println("Evento recibido del cliente: " + evento);
                    // Aquí puedes implementar la lógica para procesar el evento
                }

                clientSocket.close();
                System.out.println("Cliente desconectado.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
