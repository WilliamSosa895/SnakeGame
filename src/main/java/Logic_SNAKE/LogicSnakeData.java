/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic_SNAKE;
import Snake_Cliente_Servidor.*;
/**
 *
 * @author william
 */
public class LogicSnakeData {
    private SnakeData snakeData;
    
    public LogicSnakeData(int gridSize, int panelSize, String playerName){
        this.snakeData = new SnakeData(gridSize, panelSize, playerName);
    }
    
    public SnakeData getSnakeData(){
        return snakeData;
    }
}
