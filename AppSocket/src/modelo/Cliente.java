package modelo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Cliente {

    int puerto;
    Socket cliente;
    BufferedReader reader;

    public static void main(String[] args) {
        Cliente obj1 = new Cliente(100);
        obj1.iniciarSocketCliente();
    }

    public void iniciarSocketCliente() {
        try {
            DataOutputStream salida;
            DataInputStream entrada;
            String respuesta;
            String opcion;

            while (true) {
                reader = new BufferedReader(new InputStreamReader(System.in));

                cliente = new Socket("localhost", puerto);
                System.out.println("Bienvenido, elige una opción:\n"
                        + "1. Buscar\n"
                        + "2. Salir");
                opcion = reader.readLine();

                switch (opcion) {
                    case "1":
                        System.out.print("Qué palabra deseas buscar en el diccionario: ");
                        String palabra = reader.readLine();

                        salida = new DataOutputStream(cliente.getOutputStream());
                        salida.writeUTF(palabra);
                        break;
                    case "2":
                        // Cerrar la conexión y salir del bucle
                        salida = new DataOutputStream(cliente.getOutputStream());
                        salida.writeUTF("2");
                        cliente.close();
                        return;
                    default:
                        System.out.println("Opción no válida.");
                        break;
                }

                if (opcion.equals("1")) {
                    // Solo si la opción es "1", lee la respuesta del servidor
                    entrada = new DataInputStream(cliente.getInputStream());
                    respuesta = entrada.readUTF();
                    System.out.println("Servidor: " + respuesta);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cliente(int puerto) {
        this.puerto = puerto;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public Socket getCliente() {
        return cliente;
    }

    public void setCliente(Socket cliente) {
        this.cliente = cliente;
    }

}
