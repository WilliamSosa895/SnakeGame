/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Snake_Cliente_Servidor;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author william
 */
public class SnakeDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<int[]> segmentos; 
    private int r, g, b;           

    public SnakeDTO(List<int[]> segmentos, int r, int g, int b) {
        this.segmentos  = segmentos;
        this.r = r; this.g = g; this.b = b;
    }

    public List<int[]> getSegmentos() {
        return segmentos;
    }
    public int getR() { return r; }
    public int getG() { return g; }
    public int getB() { return b; }
}