/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Snake_Cliente_Servidor;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 *
 * @author william
 */
public class SnakeData {
    private List<int[]> segmentos = new ArrayList<>();
    private String playerName;
    private String direction = "de", nextDirection = "de";
    private int gridSize, panelSize;
    int r, g, b;
    ObjectOutputStream out;

    public SnakeData(int gridSize, int panelSize, String playerName) {
        this.gridSize = gridSize;
        this.panelSize = panelSize;
        this.playerName = playerName;

        Random rnd = new Random();
        this.r = rnd.nextInt(256);
        this.g = rnd.nextInt(256);
        this.b = rnd.nextInt(256);

        int centerX = gridSize/2;
        int centerY = gridSize/2;
        if (playerName.hashCode() % 2 == 0) {
            centerX = gridSize/4;
        } else {
            centerX = 3*gridSize/4;
        }
        segmentos.add(new int[]{centerX-1, centerY});
        segmentos.add(new int[]{centerX,   centerY});
    }

    public void setOutput(ObjectOutputStream out) {
        this.out = out;
    }

    public List<int[]> getSegments() {
        return new ArrayList<>(segmentos);
    }

    public int[] getHead() {
        return segmentos.get(segmentos.size() - 1);
    }

    public void setNextDirection(String dir) {
        if ((direction.equals("de") || direction.equals("iz")) && (dir.equals("ar") || dir.equals("ab")))
            this.nextDirection = dir;
        if ((direction.equals("ar") || direction.equals("ab")) && (dir.equals("de") || dir.equals("iz")))
            this.nextDirection = dir;
    }

    public void advance(int gridSize) {
        direction = nextDirection;
        int[] head = segmentos.get(segmentos.size()-1);
        int dx=0, dy=0;
        switch(direction) {
            case "de": dx=1; break;
            case "iz": dx=-1; break;
            case "ar": dy=-1; break;
            case "ab": dy=1; break;
        }
        int nx = Math.floorMod(head[0]+dx, gridSize);
        int ny = Math.floorMod(head[1]+dy, gridSize);

        boolean selfCollision = false;
        for (int[] seg : segmentos) {
            if (seg[0]==nx && seg[1]==ny) {
                selfCollision = true;
                break;
            }
        }

        if (selfCollision) {
            segmentos.clear();
            int mid = gridSize/2;
            segmentos.add(new int[]{mid-1, mid});
            segmentos.add(new int[]{mid,   mid});
            direction = nextDirection = "de";
        } else {
            segmentos.add(new int[]{nx, ny});
            segmentos.remove(0);
        }
    }

    public boolean checkAndGrow(int foodX, int foodY) {
        int[] head = segmentos.get(segmentos.size()-1);
        if (head[0]==foodX && head[1]==foodY) {

            int[] tail = segmentos.get(0);
            segmentos.add(0, new int[]{tail[0], tail[1]});
            return true;
        }
        return false;
    }
}