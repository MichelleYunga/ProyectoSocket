
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

            System.out.println("Esperando conexiones en el puerto: " + puerto);
            servidor = new ServerSocket(puerto);

            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress());

                DataInputStream entrada = new DataInputStream(cliente.getInputStream());
                String comando = entrada.readUTF();
                //recibido
                System.out.println(comando);

                if (comando.equalsIgnoreCase("2")) {
                    System.out.println("Conexion cerrada");
                    cliente.close();
                    break;
                }

                String resultado = diccionario.get(comando);

                if (resultado == null) {
                    System.out.println("Palabra no encontrada en el diccionario.");
                    resultado = "Palabra no encontrada";
                }

                DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());
                salida.writeUTF(resultado);
                System.out.println("Resultado para el cliente: " + resultado);

                // cliente.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                servidor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void cargarDiccionario() {
        diccionario = new HashMap<>();
        diccionario.put("moto", "vehiculo de dos llantas");
        diccionario.put("carro", "vehiculo de cuatro llantas");
        diccionario.put("avion", "vehiculo aereo de dos turbinas");
        diccionario.put("helicoptero", "vehiculo de dos elices");
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
