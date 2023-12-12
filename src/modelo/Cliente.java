package modelo;
//Cliente solicita la conexion

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
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

             while (true){
                reader = new BufferedReader(new InputStreamReader(System.in));

                this.cliente = new Socket("localhost", puerto);

                System.out.print("Que palabra desea buscar en el diccionario (o escriba 'salir' para finalizar): ");
                String palabra = reader.readLine().toLowerCase();

                
                salida = new DataOutputStream(this.cliente.getOutputStream());
                salida.writeUTF(palabra);

                if (palabra.equalsIgnoreCase("salir")) {           
                    break;
                }
                 
                entrada = new DataInputStream(cliente.getInputStream());
                respuesta = entrada.readUTF();
                
       
                System.out.println("Servidor: " + respuesta);

             // Cerrar el cliente después de cada interacción
                cliente.close();
             }
  
        } catch (IOException e) {
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
