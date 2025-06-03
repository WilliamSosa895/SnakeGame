/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic_SNAKE;
import Snake_Cliente_Servidor.*;
import java.util.List;
/**
 *
 * @author william
 */
public class LogicGameState {
    private GameState gameState;
    
    public LogicGameState(List<SnakeDTO> snakes, int foodX, int foodY){
        this.gameState = new GameState(snakes,foodX,foodY);
    }
    
    public GameState getGameState(){
        return gameState;
    }
}
