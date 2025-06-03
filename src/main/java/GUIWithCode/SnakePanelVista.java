/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIWithCode;

import GUIWithCode.PanelSnakeMulti;
import Snake_Cliente_Servidor.SnakeMessage;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
/**
 *
 * @author william
 */
public class SnakePanelVista extends JFrame {

    private PanelSnakeMulti panel;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String playerName;

    public SnakePanelVista(String playerName, ObjectOutputStream out, ObjectInputStream in) {
        this.playerName = playerName;
        this.out = out;
        this.in = in;

        initComponents();
        setTitle("Snake - Jugador: " + playerName);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panel = new PanelSnakeMulti(800, 30);
        add(panel);
        panel.setBounds(10, 10, 800, 800);
        panel.setOpaque(false);

        requestFocus(true);

        new Thread(this::receiveLoop).start();
    }

    private void receiveLoop() {
        try {
            while (true) {
                SnakeMessage msg = (SnakeMessage) in.readObject();
                if (msg.getType() == SnakeMessage.Type.STATE_UPDATE) {
                    panel.updateFromGameState(msg.getState());
                    panel.repaint();

                } else if (msg.getType() == SnakeMessage.Type.GAME_OVER) {
                    String winner = msg.getPlayerName();
                    if (winner == null || winner.isBlank()) {
                        JOptionPane.showMessageDialog(
                            this,
                            "Game Over. ¡Empate!",
                            "Fin de la Partida",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    } else if (winner.equals(playerName)) {
                        JOptionPane.showMessageDialog(
                            this,
                            "Game Over. ¡Ganaste! (" + winner + ")",
                            "Fin de la Partida",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                            this,
                            "Game Over. Ganador: " + winner,
                            "Fin de la Partida",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                    dispose();
                    return;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Desconectado del servidor",
                "Conexión perdida",
                JOptionPane.ERROR_MESSAGE
            );
            dispose();
        }
    }


    private void formKeyPressed(java.awt.event.KeyEvent evt) {
        try {
            String dir = null;
            switch (evt.getKeyCode()) {
                case KeyEvent.VK_LEFT:  dir = "iz"; break;
                case KeyEvent.VK_RIGHT: dir = "de"; break;
                case KeyEvent.VK_UP:    dir = "ar"; break;
                case KeyEvent.VK_DOWN:  dir = "ab"; break;
                default: return;
            }
            SnakeMessage m = new SnakeMessage(SnakeMessage.Type.DIRECTION, playerName, dir);
            out.writeObject(m);
            out.reset();
        } catch (Exception e) {
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setSize(820, 840);
        setLocationRelativeTo(null);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
    }
}