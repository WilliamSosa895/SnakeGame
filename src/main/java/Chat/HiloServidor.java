/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
import javax.swing.DefaultListModel;

/**
 *
 * @author william
 */
public class HiloServidor extends Thread {

    private static Vector<HiloServidor> usuariosActivos = new Vector<>();

    private Socket clienteSocket;
    private Servidor serverGUI;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private ObjectOutputStream salidaObjeto;
    private String nombre;

    public HiloServidor(Socket socketCliente, String nombre, Servidor serv) throws Exception {
        this.clienteSocket = socketCliente;
        this.serverGUI = serv;
        this.nombre = nombre;

        this.entrada = new DataInputStream(clienteSocket.getInputStream());
        this.salida = new DataOutputStream(clienteSocket.getOutputStream());
        this.salidaObjeto = new ObjectOutputStream(clienteSocket.getOutputStream());

        usuariosActivos.add(this);

        broadcast(nombre + " se ha conectado.");
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = entrada.readUTF();
                serverGUI.mensajeria("Recibido: " + msg);
                broadcast(msg);
            }
        } catch (Exception e) {
        } finally {
            usuariosActivos.remove(this);
            broadcast(nombre + " se ha desconectado.");
            try { clienteSocket.close(); } catch (Exception ignored) {}
        }
    }

    private void broadcast(String msg) {
        for (HiloServidor user : usuariosActivos) {
            try {
                user.salida.writeUTF(msg);
                DefaultListModel<String> model = new DefaultListModel<>();
                for (HiloServidor u : usuariosActivos) {
                    model.addElement(u.nombre);
                }
                user.salidaObjeto.writeObject(model);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        serverGUI.mensajeria(">> " + msg);
    }
}