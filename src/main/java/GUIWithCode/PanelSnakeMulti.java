/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIWithCode;

import Snake_Cliente_Servidor.GameState;
import Snake_Cliente_Servidor.SnakeDTO;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
/**
 *
 * @author william
 */
public class PanelSnakeMulti extends JPanel {
    private int gridSize, cellSize;
    private int offset;
    private List<SnakeDTO> snakes = new ArrayList<>();
    private int foodX, foodY;

    public PanelSnakeMulti(int panelPixels, int gridSize) {
        this.gridSize = gridSize;
        this.cellSize = panelPixels / gridSize;
        this.offset   = panelPixels % gridSize;
    }

    public void updateFromGameState(GameState gs) {
        this.snakes = gs.getSnakes();
        this.foodX   = gs.getFoodX();
        this.foodY   = gs.getFoodY();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.GRAY);
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                g.fillRect(offset/2 + x*cellSize, offset/2 + y*cellSize, cellSize-1, cellSize-1);
            }
        }

        g.setColor(Color.RED);
        g.fillRect(offset/2 + foodX*cellSize, offset/2 + foodY*cellSize, cellSize-1, cellSize-1);

        for (SnakeDTO sd : snakes) {
            g.setColor(new Color(sd.getR(), sd.getG(), sd.getB()));
            for (int[] seg : sd.getSegmentos()) {
                g.fillRect(offset/2 + seg[0]*cellSize, offset/2 + seg[1]*cellSize, cellSize-1, cellSize-1);
            }
        }
    }
}