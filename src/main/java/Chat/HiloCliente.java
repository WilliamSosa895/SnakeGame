/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author william
 */
public class HiloCliente extends Thread {
    private Socket socketCliente;
    private DataInputStream entrada;
    private ObjectInputStream entradaObjeto;
    private Cliente clienteGUI;

    public HiloCliente(Socket socketCliente, Cliente clienteGUI) throws Exception {
        this.socketCliente = socketCliente;
        this.clienteGUI = clienteGUI;
        this.entrada = new DataInputStream(socketCliente.getInputStream());
        this.entradaObjeto = new ObjectInputStream(socketCliente.getInputStream());
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = entrada.readUTF();
                clienteGUI.mensajeria(msg);
                DefaultListModel<String> model = (DefaultListModel<String>) entradaObjeto.readObject();
                clienteGUI.actualizarLista(model);
            }
        } catch (Exception e) {
            clienteGUI.mensajeria("¡Desconectado del servidor!");
        }
    }
}