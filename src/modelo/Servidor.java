package modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Servidor {

    ServerSocket servidor;
    Socket cliente;
    Map<String, String> diccionario;

    public static void main(String[] args) {
        Servidor obj1 = new Servidor();
        obj1.iniciarSocketServidor(100);
    }

    public void iniciarSocketServidor(int puerto) {
        try {
            cargarDiccionario();

            DataInputStream entrada;
            DataOutputStream salida;

            System.out.println("Esperando conexiones en el puerto: " + puerto);
            servidor = new ServerSocket(puerto);

            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress());

                entrada = new DataInputStream(cliente.getInputStream());
                String lenguajeP = entrada.readUTF();

                if (lenguajeP.equalsIgnoreCase("salir")) {
                    System.out.println("");
                    System.out.println("El cliente ha solicitado salir.");
                    break;
                }

                String resultado = diccionario.get(lenguajeP);

                if (resultado == null) {
                    System.out.println("Palabra no encontrada");
                    resultado="Palabra no encontrada";        
                }
                
                salida = new DataOutputStream(cliente.getOutputStream());
                salida.writeUTF(resultado);
                System.out.println("Resultado para el cliente: " + resultado);
                
                cliente.close();
            }

            servidor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void cargarDiccionario() {
        diccionario = new HashMap<>();
        diccionario.put("java", "Lenguaje de programación");
        diccionario.put("python", "Lenguaje de programación");
        diccionario.put("html", "Lenguaje paginas web");
        diccionario.put("css", "estilos");
    }

    public Servidor() {
    }

    public ServerSocket getServidor() {
        return servidor;
    }

    public void setServidor(ServerSocket servidor) {
        this.servidor = servidor;
    }

    public Socket getCliente() {
        return cliente;
    }

    public void setCliente(Socket cliente) {
        this.cliente = cliente;
    }
}
