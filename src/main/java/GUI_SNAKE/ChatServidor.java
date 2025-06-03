/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI_SNAKE;

import GUIWithCode.ChatHiloServidor;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author william
 */
public class ChatServidor extends javax.swing.JFrame {


    private ServerSocket server;
    private final int PUERTOH = 5000;
    
    
    public ChatServidor() {
        initComponents();
        
        try {
            server = new ServerSocket(PUERTOH);
            mensajeria("Servidor escuchando en puerto " + PUERTOH);
            setVisible(true);

            new Thread(() -> {
                try {
                    while (true) {
                        Socket cliente = server.accept();
                        DataInputStream in = new DataInputStream(cliente.getInputStream());
                        String nombre = in.readUTF();
                        mensajeria("Conexión: " + nombre + " desde " + cliente.getInetAddress());
                        ChatHiloServidor hilo = new ChatHiloServidor(cliente, nombre, this);
                        hilo.start();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error en servidor: " + e.getMessage());
                }
            }).start();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se pudo iniciar servidor: " + e.getMessage());
            System.exit(1);
        }
    }
    
    public void mensajeria(String msg){
        this.jTextArea1.append(msg + "\n");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new ChatServidor().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
