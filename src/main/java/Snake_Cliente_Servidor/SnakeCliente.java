
package Snake_Cliente_Servidor;
import GUIWithCode.SnakePanelVista;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author william
 */
public class SnakeCliente {

    private String host = "172.20.10.12"; // ip de willyserver
    private int puerto = 6000;

    private String nombre;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private SnakePanelVista vista; 
    
    public SnakeCliente() {
        try {
            nombre = JOptionPane.showInputDialog("Ingresa tu nombre");
            if (nombre == null || nombre.isBlank()) System.exit(0);

            socket = new Socket(host, puerto);
            out = new ObjectOutputStream(socket.getOutputStream());
            in  = new ObjectInputStream(socket.getInputStream());

            SnakeMessage connectMsg = new SnakeMessage(SnakeMessage.Type.CONNECT, nombre);
            out.writeObject(connectMsg);
            out.reset();

            vista = new SnakePanelVista(nombre, out, in);
            vista.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage());
            System.exit(0);
        }
    }

    public void main(String[] args) {
        new SnakeCliente();
    }
}